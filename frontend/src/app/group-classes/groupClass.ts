import {Trainer} from "../one-on-one-training/trainer";
import {Gym} from "../gym/gym";

export interface GroupClass {
  id: number;
  name: string;
  trainer: Trainer;
  maxPeople: number;
  gym: Gym;
  schedule: Date;
  duration: number;
}
