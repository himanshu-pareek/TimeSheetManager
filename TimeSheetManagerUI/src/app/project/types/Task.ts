import {Link} from "./Link";

export interface Task {
	id: string,
	projectId: string,
	userId: string,
	startDate: Date,
	endDate: Date,
	_links: {
		self: Link,
		delete: Link,
		project: Link
	}
}
