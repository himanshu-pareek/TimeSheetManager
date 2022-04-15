import {Link} from "./Link";

export interface Project {
	id: string;
	name: string;
	description: string;
	externalUrl: string;
	timeSpent: number;
	startedAt: Date;
	createdBy: string;
	_links: {
		self: Link;
		start_work: Link;
		stop_work: Link;
		tasks: Link;
	};
}
