import {Pipe, PipeTransform} from "@angular/core";

const units = [
	{
		delta: 60 * 60 * 1000,
		displaySymbol: "h"
	},
	{
		delta: 60 * 1000,
		displaySymbol: "m"
	},
	{
		delta: 1000,
		displaySymbol: "s"
	}
];

@Pipe({
	name: "beautifulTimeDifference"
})
export class BeautifulTimeDifferencePipe implements PipeTransform {
	transform(timeDifference: number, ...args: any[]): any {
		let s = "";
		for (let unit of units) {
			const quantity = Math.floor(timeDifference / unit.delta);
			if (quantity > 0) {
				s += quantity + unit.displaySymbol;
			}
			timeDifference -= quantity * unit.delta;
		}
		if (s === "") s = "0";
		return s;
	}
}
