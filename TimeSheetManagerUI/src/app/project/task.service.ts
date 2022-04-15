import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Project} from "./types/Project";
import {Task} from "./types/Task";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";

const TASK_API_BASE_ENDPOINT = "http://localhost:8080/api/v1/tasks";

@Injectable({
	providedIn: 'root'
})
export class TaskService {

	constructor(
		private httpClient: HttpClient
	) {
	}

	private static cleanTask (task: Task): Task {
		task.startDate = new Date(task.startDate);
		task.endDate = new Date(task.endDate);
		return task;
	}

	public getTasks(project: Project): Promise<Task[]> {
		return new Promise((resolve, reject) => {
			try {
				this.httpClient.get<{
					_embedded: {
						taskList: Task[]
					}
				}>(
					project._links.tasks.href
				).subscribe(response => {
					console.log(response);
					resolve(response?._embedded?.taskList?.map(TaskService.cleanTask));
				});
			} catch (e) {
				console.log(e);
				reject(e);
			}
		});
	}

	deleteTask(task: Task): Observable<any> {
		return this.httpClient.delete(task._links.delete.href);
	}

	createTask(
		startDate: Date | null,
		endDate: Date | null,
		projectId: string
	): Observable<Task> {
		if (!startDate || !endDate || !projectId) throw new Error("start-date, end-date and project-id are necessary");
		return this.httpClient.post<Task>(
			TASK_API_BASE_ENDPOINT,
			{
				projectId,
				startDate,
				endDate
			}
		).pipe(map(TaskService.cleanTask));
	}
}
