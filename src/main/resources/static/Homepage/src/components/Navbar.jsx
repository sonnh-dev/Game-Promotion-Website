import React from 'react'

const Navbar = () => {
  return (
    <div className='Navbar'>
        <h1 className='title'><a href="#">Brainrot</a></h1>
        <ul className='Navbar__container'>
            <li><a href="#home">Trang chủ</a></li>
            <li><a href="#news">Tin tức</a></li>
            <li><a href="#gallery">Nhân vật</a></li>
            <li id='playNow'><a href="#game">Chơi Ngay</a></li>
        </ul>
    </div>
  )

}

export default Navbar