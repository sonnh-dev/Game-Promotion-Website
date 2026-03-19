import React from 'react'
import Navbar from './components/Navbar.jsx'
import Hero from './components/Hero.jsx'
import News from './components/News.jsx'
import Features from './components/Features.jsx'
import Gallery from './components/Gallery.jsx'
import Play from './components/Play.jsx'
import Footer from './components/Footer.jsx'

const App = () => {
  return (
    <div>
      <Navbar/>
      <Hero/>
      <Features/>
      <News/>
      <Gallery/>
      <Play/>
      <Footer/>
    </div>
  )
}

export default App