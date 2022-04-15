import {Routes} from "@angular/router";
import {ProjectListComponent} from "./project/project-list/project-list.component";
import {ProjectComponent} from "./project/project/project.component";
import {DashboardComponent} from "./dashboard/dashboard.component";

const AppRoutes: Routes = [
	{
		path: "projects/:id",
		component: ProjectComponent
	},
	{
		path: "projects",
		component: ProjectListComponent
	},
	{
		path: "**",
		component: DashboardComponent
	}
];

export { AppRoutes };
