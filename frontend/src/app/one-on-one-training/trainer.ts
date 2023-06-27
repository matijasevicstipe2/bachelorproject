import {Gym} from "../gym/gym";

export interface Trainer {
  id: number;
  firstName: string;
  lastName: string;
  age: number;
  email: string;
  availability: boolean;
  gym: Gym;
  specialty: string;
  profilePicture: string;
}
