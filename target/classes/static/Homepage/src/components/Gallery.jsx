import React, { useState, useEffect } from 'react'
import Character1 from "../assets/Gallery/Character1.webp";
import Left_Arrow from "../assets/Gallery/left-arrow.png";
import Right_Arrow from "../assets/Gallery/right-arrow.png";

const Gallery = () => {
  const [characters, setCharacters] = useState([]);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch('/api/character')
      .then(res => res.json())
      .then(data => {
        setCharacters(data);
        setLoading(false);
      })
      .catch(err => {
        console.error('Failed to fetch characters:', err);
        setLoading(false);
      });
  }, []);

  if (loading) {
    return (
      <div id='gallery' className='gallery'>
        <p style={{ textAlign: 'center' }}>Đang tải...</p>
      </div>
    );
  }

  if (characters.length === 0) {
    return (
      <div id='gallery' className='gallery'>
        <p style={{ textAlign: 'center' }}>Chưa có nhân vật nào.</p>
      </div>
    );
  }

  const character = characters[currentIndex];

  function handlePrevCharacter() {
    setCurrentIndex(currentIndex === 0 ? characters.length - 1 : currentIndex - 1);
  }

  function handleNextCharacter() {
    setCurrentIndex(currentIndex === characters.length - 1 ? 0 : currentIndex + 1);
  }

  const prevIndex = currentIndex === 0 ? characters.length - 1 : currentIndex - 1;
  const nextIndex = currentIndex === characters.length - 1 ? 0 : currentIndex + 1;
  const charImage = character.media?.url || Character1;

  return (
    <div id='gallery' className='gallery'>
      <section className='gallery__navbar'>
        {characters.map((char, idx) => (
          <h1
            className={currentIndex === idx ? "gallery__navbar-item active" : "gallery__navbar-item"}
            key={char.id}
            onClick={() => setCurrentIndex(idx)}
          >
            {char.name}
          </h1>
        ))}
      </section>
      <section className='gallery__navigation'>
        <div className='previous'>
          <h1 className='previous-name'>{characters[prevIndex].name}</h1>
          <img className='size-10 md:size-18' src={Right_Arrow} alt="Right Arrow" onClick={handlePrevCharacter} />
        </div>
        <div className='next'>
          <h1 className='next-name'>{characters[nextIndex].name}</h1>
          <img className='size-10 md:size-18' src={Left_Arrow} alt="Left Arrow" onClick={handleNextCharacter} />
        </div>
      </section>
      <img src={charImage} alt={character.name} className='character-img' />
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