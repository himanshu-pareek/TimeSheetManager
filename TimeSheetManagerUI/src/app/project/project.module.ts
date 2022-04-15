import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ProjectListComponent} from './project-list/project-list.component';
import {ProjectComponent} from './project/project.component';
import {HttpClientModule} from "@angular/common/http";
import {BeautifulTimeDifferencePipe} from "./beautiful-time-difference.pipe";
import {TaskListComponent} from './task-list/task-list.component';
import {TaskComponent} from './task/task.component';
import {DatetimePipe} from './datetime.pipe';


@NgModule({
	declarations: [
		ProjectListComponent,
		ProjectComponent,
		BeautifulTimeDifferencePipe,
		TaskListComponent,
		TaskComponent,
		DatetimePipe
	],
	exports: [
		ProjectListComponent
	],
	imports: [
		CommonModule,
		HttpClientModule
	]
})
export class ProjectModule {
}
