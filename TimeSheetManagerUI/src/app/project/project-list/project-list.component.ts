import {Component, OnInit} from '@angular/core';
import {ProjectService} from "../project.service";
import {Project} from "../types/Project";
import {OAuthEvent, OAuthService} from "angular-oauth2-oidc";
import {LOGIN_EVENT_NAMES, LOGOUT_EVENT_NAMES} from "../../util/constants";

@Component({
	selector: 'app-project-list',
	templateUrl: './project-list.component.html',
	styleUrls: ['./project-list.component.css']
})
export class ProjectListComponent implements OnInit {

	projectList: Project[] = [];
	currentProject: Project | null = null;
	projectToCreate!: any;
	isLoading: boolean = false;

	constructor(
		private projectService: ProjectService,
		private oAuthService: OAuthService
	) {
	}

	ngOnInit(): void {
		if (this.oAuthService.getAccessToken()) {
			this.getProjectList();
		}
		this.oAuthService.events.subscribe(event => {
			if (LOGIN_EVENT_NAMES.indexOf(event.type) !== -1) {
				this.getProjectList();
			}
			if (LOGOUT_EVENT_NAMES.indexOf(event.type) !== -1) {
				this.projectList = [];
			}
		});
	}

	getProjectList() {
		this.isLoading = true;
		const projectList = this.projectService.getProjects()
			.then(projectList => {
				console.log("Got project list");
				console.log(projectList);
				this.projectList = projectList;
			})
			.catch(error => {
				console.log("Got error");
				console.error(error);
			})
			.finally(() => {
				this.isLoading = false;
			});
	}

	onChildChanged(project: Project) {
		const indexOfProject = this.projectList.findIndex(item => item.id === project.id);
		if (indexOfProject === -1) return;
		this.projectList[indexOfProject] = project;
		if (!project.startedAt) {
			this.currentProject = project;
		}
		console.log(this.projectList);
	}

	onViewTasksClicked(project: Project) {
		this.currentProject = project;
	}

	handleCreateNewProjectClick() {
		this.projectToCreate = {
			name: "",
			description: "",
			externalUrl: ""
		};
	}

	createNewProject(event: any): void {
		console.log(this.projectToCreate);
		this.projectService.createProject(JSON.parse(JSON.stringify(this.projectToCreate)))
			.subscribe(response => {
				this.getProjectList();
			});

		this.projectToCreate = null;
		event.preventDefault();
	}

	handleFormFieldChange(event: any) {
		this.projectToCreate[event.target.name] = event.target.value;
	}

	handleUpdateProject (projectId: string) {
		this.projectService.getProject(projectId)
			.subscribe(project => {
				this.onChildChanged(project);
			});
	}

	handleCancelNewProjectClick() {
		this.projectToCreate = null;
	}
}
