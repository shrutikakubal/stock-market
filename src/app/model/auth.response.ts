import {User} from './user.model'
export class AuthenticationResponse {
jwtToken: string;
userDtls: User;
}
