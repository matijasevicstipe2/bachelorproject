export class Account {
  constructor(
    public firstname: string,
    public lastname: string,
    public username: string,
    public password: string,
    public birthdate: Date | null,
    public sex: string,
    public city: string,
    public citystate: string,
    public country: string
  ) {}
}
