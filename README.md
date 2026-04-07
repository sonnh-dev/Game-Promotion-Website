<h1 align="center">🎮 BrainRot Runner - Official Promotion Website</h1>

<p align="center">
Trang web chính thức dùng để quảng bá và giới thiệu tựa game <b>BrainRot Runner</b>.
<br/>
Thiết kế hiện đại, tối ưu trải nghiệm trên cả Desktop và Mobile.
</p>

<hr/>

<h2> 1. Tổng quan dự án</h2>
<p>
<b>BrainRot Runner</b> là một dự án game thể loại <b>Runner</b> với phong cách độc đáo.
Website này đóng vai trò là <i>"mặt tiền"</i> của dự án, giúp người chơi:
</p>

<ul>
  <li>Khám phá cốt truyện và dàn nhân vật đặc sắc </li>
  <li>Theo dõi các tin tức mới nhất về quá trình phát triển</li>
  <li>Trải nghiệm không khí game qua hiệu ứng thị giác trên web</li>
</ul>

<p>
<b>Điểm khác biệt:</b> Giao diện tập trung vào tương tác cao, animation mượt và tối ưu tốc độ tải.
</p>

<hr/>

<h2> 2. Demo & Media</h2>
<ul>
  <li><b>Live Demo:</b> <i>[Chưa cập nhật / GitHub Pages]</i></li>
  <li>
    <b>UI/UX Design:</b> 
    <a href="https://www.figma.com/design/rE68cX0dmb0ssuipBAfomB/BrainRot_Game_Promotion_Website?node-id=0-1&p=f&t=iW4ClfpbNpFny7zT-0" target="_blank">
      🎨 View Figma Design
    </a>
  </li>
</ul>


<hr/>

<h2> 3. Công nghệ sử dụng</h2>
<ul>
  <li><b>HTML5 & CSS3 (BEM)</b> – Cấu trúc rõ ràng, dễ maintain</li>
  <li><b>JavaScript (Vanilla)</b> – Xử lý logic và tương tác</li>
  <li><b>AOS / GSAP</b> – Animation khi scroll (nếu có)</li>
  <li><b>Figma</b> – Thiết kế UI/UX</li>
</ul>

<hr/>

<h2> 4. Cài đặt và chạy dự án (Local Development)</h2>

<p>
Phần này hướng dẫn bạn cách lấy source code từ <b>Gitea</b> và chạy website trên máy cá nhân.
</p>

<h3>📥 Bước 1: Clone repository</h3>
<pre><code>git clone https://git.lim-dxlab.space/UnNull/brain-root-runner-web.git</code></pre>

<h3>📂 Bước 2: Di chuyển vào thư mục dự án</h3>
<pre><code>cd brain-root-runner-web</code></pre>

<h3>🚀 Bước 3: Chạy dự án</h3>
<ul>
  <li>Mở file <code>index.html</code> trực tiếp bằng trình duyệt</li>
  <li>Hoặc sử dụng <b>Live Server</b> trong VS Code để chạy local server (khuyến nghị)</li>
</ul>

<hr/>

<h3>🌐 Deploy Website</h3>

<p>
Hiện tại repository được lưu trữ trên <b>Gitea</b> và <b>không hỗ trợ static hosting trực tiếp</b>.
Vì vậy, để triển khai website, bạn cần sử dụng server riêng hoặc dịch vụ hosting.
</p>

<h4>🚀 Deploy cơ bản (khuyến nghị)</h4>
<ol>
  <li>Clone project từ Gitea</li>
  <li>Upload source code lên server (VPS / Hosting)</li>
  <li>Cấu hình web server (Nginx / Apache)</li>
  <li>Truy cập website thông qua domain hoặc IP</li>
</ol>

<pre><code>git clone https://git.lim-dxlab.space/UnNull/brain-root-runner-web.git</code></pre>

<h4>⚡ Deploy tự động (nâng cao)</h4>
<ul>
  <li>Sử dụng <b>Webhooks</b> trong Gitea để trigger deploy</li>
  <li>Kết hợp với script trên server để auto pull & reload</li>
</ul>

<p>
👉 Lưu ý: Gitea trong hệ thống này chỉ đóng vai trò <b>quản lý source code</b>, không phải nền tảng hosting.
</p>


<hr/>

<h2> 5. Cấu trúc thư mục</h2>

<pre><code>
├── assets/
│   ├── images/       # Ảnh nhân vật, logo
│   ├── fonts/        # Font game
│   └── icons/        # Icon mạng xã hội
├── css/
│   ├── style.css
│   └── responsive.css
├── js/
│   └── main.js
└── index.html
</code></pre>

<hr/>

<h2> 6. Các tính năng chính</h2>

<ul>
  <li> <b>Hero Section</b> – Nổi bật với CTA</li>
  <li> <b>Character Showcase</b> – Giới thiệu nhân vật</li>
  <li> <b>Latest News</b> – Cập nhật tiến độ</li>
  <li> <b>Responsive Design</b> – Tương thích Mobile/Tablet</li>
  <li> <b>Tracking API (Coming Soon)</b></li>
</ul>

<hr/>

<h2> 7. Roadmap</h2>

<ul>
  <li>[ ] Hoàn thiện Responsive cho nhiều thiết bị</li>
  <li>[ ] Tích hợp Tracking API</li>
  <li>[ ] Thêm hiệu ứng âm thanh (BGM)</li>
  <li>[ ] Hệ thống Pre-register</li>
</ul>

<hr/>


<hr/>

<p align="center">
🔥 Made with passion for game development
</p>
