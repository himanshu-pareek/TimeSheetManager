import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {Project} from "../types/Project";
import {TaskService} from "../task.service";
import {Task} from "../types/Task";
import {ProjectService} from "../project.service";

@Component({
	selector: 'project-task-list',
	templateUrl: './task-list.component.html',
	styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit, OnChanges {

	@Input() project!: Project;
	@Output() updateProject = new EventEmitter<string>();

	taskList: Task[] = [];

	constructor(
		private taskService: TaskService,
		private projectService: ProjectService
	) {
	}

	ngOnInit(): void {
	}

	ngOnChanges(changes: SimpleChanges): void {
		this.project = changes["project"].currentValue;
		this.getTaskList();
	}

	getTaskList(): void {
		console.log("Get task list for project", this.project);
		this.taskService.getTasks(this.project)
			.then(taskList => {
				this.taskList = taskList;
			});
	}

	removeTask(taskId: string): void {
		const taskIndex = this.taskList.findIndex(task => task.id === taskId);
		if (taskIndex !== -1)
			this.taskList.splice(taskIndex, 1);
	}

	onTaskDeleted(task: Task): void {
		// 1. Fire event
		this.updateProject.emit(task.projectId);
		// 2. Remove task from the array
		this.removeTask(task.id);
	}
}
