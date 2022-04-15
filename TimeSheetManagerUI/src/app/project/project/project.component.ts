import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Project} from "../types/Project";
import {ProjectService} from "../project.service";

@Component({
	selector: 'app-project',
	templateUrl: './project.component.html',
	styleUrls: ['./project.component.css']
})
export class ProjectComponent implements OnInit {

	@Input() project!: Project;
	@Output() changed = new EventEmitter<Project>();
	@Output() viewTasksClicked = new EventEmitter<Project>();

	loading: boolean = false;

	constructor(
		private projectService: ProjectService
	) {
	}

	ngOnInit(): void {
	}

	startWorking(): void {
		this.loading = true;
		this.projectService.startWork(this.project)
			.subscribe(project => {
				console.log(project);
				this.notifyParentAboutChange(project);
				this.loading = false;
			});
	}

	stopWorking(): void {
		this.loading = true;
		this.projectService.stopWork(this.project)
			.subscribe(project => {
				console.log(project);
				this.notifyParentAboutChange(project);
				this.loading = false;
			});
	}

	notifyParentAboutChange(project: Project): void {
		this.changed.emit(project);
	}

	viewTasks() {
		this.viewTasksClicked.emit(this.project);
	}
}
