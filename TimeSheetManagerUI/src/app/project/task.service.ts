import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Project} from "./types/Project";
import {Task} from "./types/Task";
import {Observable} from "rxjs";

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
}
