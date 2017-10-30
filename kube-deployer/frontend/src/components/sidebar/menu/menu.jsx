import React from 'react'
import MenuItem from './menuItem'
import Treeview from './treeview'

export default props => (
    <ul className="sidebar-menu">
        <li className="header">MAIN NAVIGATION</li>
        <Treeview label={'Runtime'} icon={'sitemap'} >
            <MenuItem label={'Tags'} icon={'tags'} path='/'/>
            <MenuItem label={'Deploy Info'} icon={'info'} path={'#'}/>
        </Treeview>
    </ul>
)