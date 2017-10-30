import React from 'react'
import Menu from './menu/menu';

export default props => (
    <aside className="main-sidebar">

        <section className="sidebar">

            <div className="user-panel">
                <div className="pull-left image">
                    <img src="dist/img/user2-160x160.jpg" className="img-circle" alt="User Image"/>
                </div>
                <div className="pull-left info">
                    <p>&nbsp;K8sDMgr</p>
                    <a href="#"><i className="fa fa-circle text-success"></i> Online</a>
                </div>
            </div>

            <Menu/>

        </section>

    </aside>
)