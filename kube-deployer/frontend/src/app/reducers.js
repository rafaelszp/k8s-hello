import {combineReducers} from "redux";
import {reducer as formReducer} from "redux-form";
import {reducer as toastrReducer} from 'react-redux-toastr';
import userReducer from "../components/user/userReducer";

const keycloakReducer = (keycloak = {}, action) => {
    return keycloak;
}

const rootReducer = combineReducers({
    user: userReducer,
    form: formReducer,
    keycloak: keycloakReducer,
    toastr: toastrReducer
});

export default rootReducer;