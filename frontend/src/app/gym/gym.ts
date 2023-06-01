export class Gym {
  id: number;
  name: string;
  address: string;
  cityState: string;
  location: string;

  constructor(id: number, name: string, address: string, cityState: string) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.cityState = cityState;
    this.location = address + " , " + cityState;
  }
}
