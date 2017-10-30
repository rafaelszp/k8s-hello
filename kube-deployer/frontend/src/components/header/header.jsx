import React, {Component} from "react";
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';
import {logout} from '../user/userActions';

class Header extends Component {

    constructor(props) {
        super(props);

    }

    render() {
        const {logout} = this.props;
        return (

            <header className="main-header">

                <a href="index2.html" className="logo">

                    <span className="logo-mini"><b>k8s</b>D</span>

                    <span className="logo-lg"><b>K8s</b>Deploy Manager</span>
                </a>

                <nav className="navbar navbar-static-top">

                    <a href="#" className="sidebar-toggle" data-toggle="offcanvas" role="button">
                        <span className="sr-only">Toggle navigation</span>
                    </a>

                    {/*Barra de botões*/}
                    <div className="navbar-custom-menu">


                        <ul className="nav navbar-nav">
                            <li className="dropdown user user-menu">

                                {/*Botão de usuários*/}
                                <a href="#" className="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                                <span className="user-image">
                                    <i className="fa fa-user-o"></i>
                                </span>
                                    <span className="hidden-xs">{this.props.user.cn}&nbsp;</span>
                                </a>

                                {/*Dropdown com dados do usuário*/}
                                <ul className="dropdown-menu">

                                    <li className="user-header">
                                    <span className="img-circle">
                                        <i className="fa fa-user-circle fa-3x"></i>
                                    </span>
                                        <p>
                                            <strong>{this.props.user.givenName}</strong>
                                        </p>
                                        <p>
                                            <small>{this.props.user.department}-{this.props.user.physicalDeliveryOfficeName}</small>
                                        </p>
                                    </li>
                                    <li className="user-body">
                                        <div className="row">
                                            <div className="col-xs-12 text-center">
                                                <small>E-mail: </small>
                                                {this.props.user.mail}
                                            </div>
                                        </div>

                                    </li>
                                    <li className="user-footer">
                                        <div className="pull-right">
                                            <button className="btn btn-default btn-flat" onClick={logout}>
                                                <i className="fa fa-sign-out"></i>
                                                Sair
                                            </button>
                                        </div>
                                    </li>
                                </ul>

                            </li>
                        </ul>
                    </div>


                </nav>
            </header>

        );
    }

};

const mapStateToProps = state => ({user: state.user});
const mapDispatchToProps = dispatch => bindActionCreators({logout}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(Header);