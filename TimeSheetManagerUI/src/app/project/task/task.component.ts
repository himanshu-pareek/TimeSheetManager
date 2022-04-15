import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Task} from "../types/Task";
import {TaskService} from "../task.service";

@Component({
	selector: 'project-task',
	templateUrl: './task.component.html',
	styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {

	@Input() task!: Task;
	@Output() taskDeleted = new EventEmitter<Task>();

	constructor(
		private taskService: TaskService
	) {
	}

	ngOnInit(): void {
	}

	get timeSpent(): number {
		return this.task.endDate.getTime() - this.task.startDate.getTime();
	}

	deleteTask(): void {
		this.taskService.deleteTask(this.task)
			.subscribe(response => {
				this.taskDeleted.emit(this.task);
			});
	}

}
