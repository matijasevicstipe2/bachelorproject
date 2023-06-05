export class Account {
  constructor(
    public firstname: string,
    public lastname: string,
    public username: string,
    public email: string,
    public password: string,
    public birthdate: Date | null,
    public sex: string,
    public address: string,
    public cityState: string
  ) {}
}
