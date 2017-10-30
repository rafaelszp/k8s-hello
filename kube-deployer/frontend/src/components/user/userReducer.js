import {USER_LOGGED,USER_SEARCHED} from './userConstants';

const INITIAL_STATE ={
    givenName: '',
    cn: '',
    department: '',
    title:'',
    description: "",
    mail: "",
    physicalDeliveryOfficeName: ""
}

export default (state=INITIAL_STATE,action) => {
    switch(action.type){
        case USER_SEARCHED:
            return {...state, ...action.payload}
        default:
            return {...state};
    }
}