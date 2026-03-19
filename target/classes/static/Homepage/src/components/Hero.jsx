import React from 'react'
import PlayIcon from "../assets/Hero/Play_circle.svg";
import Character_1 from "../assets/Hero/Tralalero-Tralala.webp"
import Character_2 from "../assets/Hero/Bombombini-Gusini.webp"
import Iphone_Frame from "../assets/Hero/Iphone17.png";
import { useGSAP } from '@gsap/react';
import {gsap} from 'gsap';
const Hero = () => {
    useGSAP(()=>{
        gsap.to(".hero-advertisement-cha1",{
            y:20,
            rotate:-2,
            ease:"power1",
            repeat:-1,
            yoyo:true,
            duration:1,
        });
        gsap.to(".hero-advertisement-cha2",{
            x:10,
            y:10,
            duration:1,
            ease:"power1.inOut",
            repeat:-1,
            yoyo:true,
        })
    },[])
  return (
    <div id='home' className='hero'>
        <section className='hero__container'>
            <h2 className='hero-title'> 
                <span className='hero__container-title'>Brain Rot</span> <span className='hero__container-game'>Runner</span> . Hãy thư giãn để sẵn sàng chơi 
            </h2>
            <p className='hero-paragraph'>Hãy đắm mình vào cuộc chạy không bao giờ dừng</p>
            <a href="#game">
            <button className='hero-btn'><img src={PlayIcon} alt="Play Icon" className='btn-icon' /> Chơi Ngay</button>
            </a>
        </section>
        <section className='hero-advertisement'>
            <img className='hero-advertisement-cha1' src={Character_1} alt="Tralalero-Tralala" />
            <img className='hero-advertisement-iphone' src={Iphone_Frame} alt="IPhone_17" />
            <img className='hero-advertisement-cha2' src={Character_2} alt="Tralalero-Tralala" />
        </section>
    </div>
  )
}

export default Hero