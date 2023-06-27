export interface Gym {
  id: number;
  name: string;
  address: string;
  cityState: string;
  openingHours: string;
  peopleInGym: number | undefined;
  profilePicture: ArrayBuffer | string | null;
}
