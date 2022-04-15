import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
	name: 'datetime'
})
export class DatetimePipe implements PipeTransform {

	transform(date: Date, ...args: unknown[]): string {
		return date.toLocaleDateString() + " " + date.toLocaleTimeString();
	}

}
