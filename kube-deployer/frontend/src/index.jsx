import React from 'react';
import ReactDOM from 'react-dom';
import {Provider} from 'react-redux';
import promise from 'redux-promise';
import multi from 'redux-multi';
import thunk from 'redux-thunk';
import {applyMiddleware, createStore} from 'redux';


import reducers from './app/reducers';
import App from './app/app';

const devTools = window.__REDUX_DEVTOOLS_EXTENSION__
    && window.__REDUX_DEVTOOLS_EXTENSION__()

let store;
if(ENVIRONMENT==='development'){
    store = applyMiddleware(multi, promise,thunk)(createStore)(reducers,devTools);
    console.log(`Environment: ${process.env.NODE_ENV}`);
}else{
    store = applyMiddleware(multi, promise,thunk)(createStore)(reducers);
}

const app = (<Provider store={store}>
        <App/>
    </Provider>)

ReactDOM.render(app,document.getElementById('app'));