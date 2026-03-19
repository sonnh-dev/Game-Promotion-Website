import React, { useState } from 'react'
import Character1 from "../assets/Gallery/Character1.webp";
import Left_Arrow from "../assets/Gallery/left-arrow.png";
import Right_Arrow from "../assets/Gallery/right-arrow.png";
const Gallery = () => {
  const array = [
    {   
        id:0,
        image:Character1,
        name:"Tung Tung Tung Sahur",
        description:"Tung Tung Tung Sahur is a mysterious wooden guardian "+
        "that comes to life at night. Carved from an ancient oak, this strange"+
        " creature prowls with its massive cudgel, producing hollow knocking sounds as it walks. "
    },
        {
        id:1,
        image:Character1,
        name:"Tralalero Tralala",
        description:"Tung Tung Tung Sahur is a mysterious wooden guardian "+
        "that comes to life at night. Carved from an ancient oak, this strange"+
        " creature prowls with its massive cudgel, producing hollow knocking sounds as it walks. "
    },
        {
        id:2,
        image:Character1,
        name:"Balerinna Cappucinna",
        description:"Tung Tung Tung Sahur is a mysterious wooden guardian "+
        "that comes to life at night. Carved from an ancient oak, this strange"+
        " creature prowls with its massive cudgel, producing hollow knocking sounds as it walks. "
    },
        {
        id:3,
        image:Character1,
        name:"Bombardino Crocodilo",
        description:"Tung Tung Tung Sahur is a mysterious wooden guardian "+
        "that comes to life at night. Carved from an ancient oak, this strange"+
        " creature prowls with its massive cudgel, producing hollow knocking sounds as it walks. "
    }
  ]
   const [character , setCharacter] = useState(array[0]);
   function handlePrevCharacter(){
     setCharacter(character.id === 0 ? array[array.length - 1] : array[character.id - 1]);
   }
    function handleNextCharacter(){
     setCharacter(character.id === array.length - 1 ? array[0] : array[character.id + 1]);
   }
    return (
    <div id='gallery' className='gallery'>
        <section className='gallery__navbar'>
            {array.map((char)=>(
                <h1 className={character.id === char.id ? "gallery__navbar-item active":"gallery__navbar-item"} key={char.id} >{char.name}</h1>
            ))}
        </section>
        <section className='gallery__navigation'>
            <div className='previous'>
                <h1 className='previous-name'>{character.id === 0 ? `${array[array.length - 1].name}` : `${array[character.id - 1].name}`}</h1>
                <img className='size-10 md:size-18' src={Right_Arrow} alt="Right Arrow" onClick={handlePrevCharacter} />
            </div>
            <div className='next'>
                <h1 className='next-name'>{character.id === array.length - 1 ? `${array[0].name}` : `${array[character.id + 1].name}`}</h1>
                <img className='size-10 md:size-18' src={Left_Arrow} alt="Left Arrow" onClick={handleNextCharacter} />
            </div>
        </section>
        <img src={character.image} alt={character.name} className='character-img'/>
        <section className=' gallery__character-display'>
            <div className='gallery__name-box'>
                <p>Name:</p>
                 <h2 className='gallery-name'>{character.name}</h2>
            </div>
            <div className='gallery__description'>
                <p>{character.description}</p>
            </div>
        </section>
    </div>
  )
}

export default Gallery