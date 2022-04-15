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
	displayNewTaskForm: boolean = false;
	startDateNewTask: Date | null = null;
	endDateNewTask: Date | null = null;

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

	addTask(task: Task): void {
		this.taskList = [
			...this.taskList,
			task
		];
	}

	onTaskDeleted(task: Task): void {
		// 1. Fire event
		this.updateProject.emit(task.projectId);
		// 2. Remove task from the array
		this.removeTask(task.id);
	}

	onTaskCreated(task: Task): void {
		this.updateProject.emit(task.projectId);
		this.addTask(task);
	}

	viewAddNewTaskForm() {
		this.displayNewTaskForm = !this.displayNewTaskForm;
	}

	handleStartDateChange(event: any) {
		if (event.target.value) {
			this.startDateNewTask = new Date(event.target.value);
		} else {
			this.startDateNewTask = null;
		}
	}

	handleEndDateChange(event: any) {
		if (event.target.value) {
			this.endDateNewTask = new Date(event.target.value);
		} else {
			this.endDateNewTask = null;
		}
	}

	createNewTask(event: SubmitEvent) {
		event.preventDefault();
		console.log(this.startDateNewTask + " - " + this.endDateNewTask);
		this.taskService.createTask(this.startDateNewTask, this.endDateNewTask, this.project.id)
			.subscribe(task => {
				this.onTaskCreated(task);
			})
	}

	get disableCreateTaskButton() {
		return !this.startDateNewTask || !this.endDateNewTask;
	}
}
