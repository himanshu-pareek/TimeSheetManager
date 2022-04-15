import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Project} from "./types/Project";
import { map } from "rxjs/operators";

const PROJECTS_API_BASE_URL = "http://localhost:8080/api/v1/projects/";

@Injectable({
	providedIn: 'root'
})
export class ProjectService {

	constructor(
		private httpClient: HttpClient
	) {
	}

	private static cleanProject(project: Project): Project {
		if (project.startedAt) {
			project.startedAt = new Date(project.startedAt);
		}
		return project;
	}

	getProjects = async (): Promise<Project[]> => {
		return new Promise((resolve, reject) => {
			try {

			this.httpClient.get<{
				_embedded: {
					projectList: Project[]
				}
			}>(PROJECTS_API_BASE_URL)
				.subscribe(response => {
					console.log({response});
					resolve(response?._embedded?.projectList?.map(ProjectService.cleanProject));
				});
			} catch (e) {
				console.log(e);
				reject(e);
			}
		});
	};

	getProject = (projectId: string): Observable<Project> => {
		return this.httpClient.get<Project>(
			`${PROJECTS_API_BASE_URL}${projectId}`
		);
	}

	startWork = (project: Project): Observable<Project> => {
		if (!project._links.start_work) throw new Error("Can not start working on the project");
		return this.httpClient.post<Project>(
			project._links.start_work.href,
			{},
			{}
		).pipe(map(ProjectService.cleanProject));
	}

	stopWork = (project: Project): Observable<Project> => {
		if (!project._links.stop_work) throw new Error("Can not stop working on the project");
		return this.httpClient.post<Project>(
			project._links.stop_work.href,
			{},
			{}
		).pipe(map(ProjectService.cleanProject));
	}

	createProject(data: any): Observable<Project> {
		return this.httpClient.post<Project>(
			`${PROJECTS_API_BASE_URL}`,
			data
		).pipe(map(ProjectService.cleanProject));
	}
}
