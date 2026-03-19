import React from 'react'
import Facebook from "../assets/Footer/facebook.png"
import Instagram from "../assets/Footer/instagram.png"
import Twitter from "../assets/Footer/twitter.png"
import Send from "../assets/Footer/send.svg"
const Footer = () => {
return (
    <section>
      <div className="bg-gray-800 text-white p-10 px-15 flex flex-col max-lg:text-center items-center 
      lg:flex-row justify-around lg:h-[50vh]">
        <div className="flex flex-col items-center md:items-start gap-5 lg:ml-35 ">
          <div className="flex items-center justify-center">
            <span>
              {/* <img src={Logo} alt="Dx Lab Logo" className="size-14" /> */}
            </span>
            <h1 className="text-3xl font-semibold stroke-text max-lg:text-center">
              Brainrot
            </h1>
          </div>
          <ul className="flex gap-5 justify-between items-center">
            <li className="bg-gray-500 rounded-full p-2">
              <a href="#">
                <img
                  src={Facebook}
                  alt="Facebook"
                  className=" size-3 md:size-4"
                />
              </a>
            </li>
            <li className="bg-gray-500 rounded-full p-2">
              <a href="#">
                <img
                  src={Instagram}
                  alt="Instagram"
                  className=" size-3 md:size-4"
                />
              </a>
            </li>
            <li className="bg-gray-500 rounded-full p-2">
              <a href="#">
                <img
                  src={Twitter}
                  alt="Twitter"
                  className=" size-3 md:size-4"
                />
              </a>
            </li>
          </ul>
        </div>
        <div className=" sm:flex justify-end gap-8 hidden ">
          <div className="space-y-1">
            <h1 className="font-semibold text-[20px] mb-4">
              Liên hệ với chúng tôi
            </h1>
            <p>Địa chỉ: FPT University - Hoa Lac Campus</p>
            <p>Khu CNC Hòa Lạc, Km29, ĐCT08, Thạch Hoà, Thạch Thất, Hà Nội</p>
            <p>Điện thoại: (+84) 936 168 165</p>
            <p>Email: duongtb@fe.edu.vn</p>
            <p>Website: www.fpt.edu.vn</p>
          </div>
          <div className="flex flex-col items-center ">
            <h1 className="font-semibold text-[20px] mb-5">
              Đăng ký để nhận thông tin mới
            </h1>
            <div className="flex items-center rounded-md bg-gray-600 p-1">
              <input
                type="email"
                placeholder="Your email address"
                className=" p-1 border-0 outline-0"
              />
              <button>
                <img src={Send} alt="Send" className="size-5" />
              </button>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}

export default Footer