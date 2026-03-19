import React from 'react'
import World_Icon from "../assets/Features/Globe.svg";
import Play_Icon from "../assets/Features/Play.svg";
import Star_Icon from "../assets/Features/Star.svg";
const Features = () => {
  return (
    <div id='news' className='features'>
     <section className="feature feature1">
        <img src={World_Icon} alt="Globe Icon" className='feature-icon'/>
        <div className='feature__container'>
            <h3 className='feature__container-title'>Hệ thống</h3>
            <p>Chạy càng xa xếp hạng càng cao</p>
        </div>
     </section>
     <section className="feature feature2">
        <img src={Play_Icon} alt="Play Icon" className='feature-icon'/>
        <div className='feature__container'>
            <h3 className='feature__container-title'>Chức năng</h3>
            <p>map có thể kéo dài vô hạn</p>
        </div>
     </section>     
     <section className="feature feature3">
        <img src={Star_Icon} alt="Star Icon" className='feature-icon'/>
        <div className='feature__container'>
            <h3 className='feature__container-title'>Tính Điểm</h3>
            <p>Chạy càng xa tích lũy điểm càng cao</p>
        </div>
     </section>
    </div> 
);
}

export default Features