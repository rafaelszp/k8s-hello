/**
 * Created by rafael on 4/24/17.
 */

import axios from 'axios';
import {USER_SEARCHED,USER_DOLOGOUT} from './userConstants'
import {toastr} from 'react-redux-toastr';

const USER_API=`${API_URL}/api/users`;

const config = (state) => {

    let axiosConfig ={
        headers: {
            // 'Content-Type': 'application/json',
            // Accept: 'application/json',
            // Authorization: `Bearer ${state.keycloak.token}`
        }
    }

    axios.interceptors.request.use(config=>{
        config.headers = {...config.headers, ...axiosConfig}
        return config;
    });

    return axiosConfig;


}

export const logout = () => {
    return (dispatch,getState) => {
        getState().keycloak.logout().success(()=>dispatch({type:USER_DOLOGOUT}));
    }
}

export const searchUser = (id) => {
    return (dispatch,getState) =>{
        axios.get(`${USER_API}/${id}`,config(getState()))
            .then(resp=>{
                dispatch({
                    type: USER_SEARCHED,
                    payload: resp.data
                })
            })
    }
}


export const changePassword = () => {
    return (dispatch,getState) => {
        let data = {...getState().user, unicodePwd: getState().form.userForm.values.novaSenha}
        axios.post(`${USER_API}/change_password`,data,config(getState()))
            .then(resp=>{
                toastr.success('Info','Senha alterada com sucesso!')
                toastr.success('Info','A senha será atualizada no OFFICE365/E-MAIL em até 3h!')
            })
            .catch(e=>{
                e.response.data.errors.forEach(error => toastr.error('ERRO',`Erro ao realizar alteração de senha motivo:\n ${error}`));
            });
    }
}




