package com.dxlab.gamepromotionweb.controller;

import com.dxlab.gamepromotionweb.entity.Characters;
import com.dxlab.gamepromotionweb.entity.Media;
import com.dxlab.gamepromotionweb.entity.News;
import com.dxlab.gamepromotionweb.entity.User;
import com.dxlab.gamepromotionweb.form.CharacterForm;
import com.dxlab.gamepromotionweb.form.MediaForm;
import com.dxlab.gamepromotionweb.form.NewsForm;
import com.dxlab.gamepromotionweb.repository.CharactersRepository;
import com.dxlab.gamepromotionweb.repository.MediaRepository;
import com.dxlab.gamepromotionweb.repository.NewsRepository;
import com.dxlab.gamepromotionweb.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dashboard")
public class DashboardPagesController {

    private final CharactersRepository charactersRepository;
    private final NewsRepository newsRepository;
    private final MediaRepository mediaRepository;
    private final UserRepository userRepository;

    public DashboardPagesController(CharactersRepository charactersRepository,
                                    NewsRepository newsRepository,
                                    MediaRepository mediaRepository,
                                    UserRepository userRepository) {
        this.charactersRepository = charactersRepository;
        this.newsRepository = newsRepository;
        this.mediaRepository = mediaRepository;
        this.userRepository = userRepository;
    }

    private void addCommonModel(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("userName", principal.getName());
        }
        model.addAttribute("zoneId", ZoneId.systemDefault());
    }

    private void populateNewsPage(Model model, Principal principal, NewsForm newsForm, Integer editingNewsId) {
        addCommonModel(model, principal);
        model.addAttribute("newsItems", newsRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")));
        model.addAttribute("newsForm", newsForm);
        model.addAttribute("newsEditing", editingNewsId != null);
        model.addAttribute("editingNewsId", editingNewsId);
    }

    private void populateMediaPage(Model model, Principal principal, MediaForm mediaForm, Integer editingMediaId) {
        addCommonModel(model, principal);
        model.addAttribute("mediaItems", mediaRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        model.addAttribute("mediaForm", mediaForm);
        model.addAttribute("mediaEditing", editingMediaId != null);
        model.addAttribute("editingMediaId", editingMediaId);
    }

    private void populateCharactersPage(Model model, Principal principal,
                                        CharacterForm characterForm,
                                        Integer editingCharacterId) {
        addCommonModel(model, principal);
        model.addAttribute("characterItems", charactersRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        model.addAttribute("mediaOptions", mediaRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        model.addAttribute("characterForm", characterForm);
        model.addAttribute("characterEditing", editingCharacterId != null);
        model.addAttribute("editingCharacterId", editingCharacterId);
    }

    private User resolveCurrentUser(Principal principal) {
        if (principal == null) {
            return null;
        }
        return userRepository.findByUsername(principal.getName());
    }

    private NewsForm toNewsForm(News news) {
        NewsForm newsForm = new NewsForm();
        newsForm.setTitle(news.getTitle());
        newsForm.setContent(news.getContent());
        newsForm.setViewed(news.getViewed());
        return newsForm;
    }

    private void applyNewsForm(News news, NewsForm newsForm, Principal principal, boolean isNew) {
        news.setTitle(newsForm.getTitle().trim());
        news.setContent(newsForm.getContent().trim());
        news.setViewed(newsForm.getViewed());
        if (isNew || news.getCreatedAt() == null) {
            news.setCreatedAt(Instant.now());
        }

        User currentUser = resolveCurrentUser(principal);
        if (currentUser != null) {
            news.setUser(currentUser);
        }
    }

    private MediaForm toMediaForm(Media media) {
        MediaForm mediaForm = new MediaForm();
        mediaForm.setUrl(media.getUrl());
        mediaForm.setType(media.getType());
        mediaForm.setEntityType(media.getEntityType());
        mediaForm.setEntityId(media.getEntityId());
        return mediaForm;
    }

    private void applyMediaForm(Media media, MediaForm mediaForm) {
        media.setUrl(mediaForm.getUrl().trim());
        media.setType(mediaForm.getType().trim());
        media.setEntityType(mediaForm.getEntityType().trim());
        media.setEntityId(mediaForm.getEntityId());
    }

    private CharacterForm toCharacterForm(Characters character) {
        CharacterForm characterForm = new CharacterForm();
        characterForm.setId(character.getId());
        characterForm.setName(character.getName());
        characterForm.setMediaId(character.getMedia().getId());
        characterForm.setDescription(character.getDescription());
        return characterForm;
    }

    private void applyCharacterForm(Characters character, CharacterForm characterForm, Media media) {
        character.setId(characterForm.getId());
        character.setName(characterForm.getName().trim());
        character.setMedia(media);
        character.setDescription(characterForm.getDescription().trim());
    }

    @GetMapping("/news")
    public String news(Model model, Principal principal) {
        NewsForm newsForm = new NewsForm();
        newsForm.setViewed(0);
        populateNewsPage(model, principal, newsForm, null);
        return "dashboard/news";
    }

    @GetMapping("/news/{id}/edit")
    public String editNews(@PathVariable Integer id, Model model, Principal principal,
                           RedirectAttributes redirectAttributes) {
        Optional<News> existingNews = newsRepository.findById(id);
        if (existingNews.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy tin tức cần sửa.");
            return "redirect:/dashboard/news";
        }

        populateNewsPage(model, principal, toNewsForm(existingNews.get()), id);
        return "dashboard/news";
    }

    @PostMapping("/news")
    public String createNews(@Valid @ModelAttribute("newsForm") NewsForm newsForm,
                             BindingResult bindingResult,
                             Model model,
                             Principal principal,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            populateNewsPage(model, principal, newsForm, null);
            return "dashboard/news";
        }

        News news = new News();
        applyNewsForm(news, newsForm, principal, true);
        newsRepository.save(news);

        redirectAttributes.addFlashAttribute("successMessage", "Đã thêm tin tức thành công.");
        return "redirect:/dashboard/news";
    }

    @PostMapping("/news/{id}")
    public String updateNews(@PathVariable Integer id,
                             @Valid @ModelAttribute("newsForm") NewsForm newsForm,
                             BindingResult bindingResult,
                             Model model,
                             Principal principal,
                             RedirectAttributes redirectAttributes) {
        Optional<News> existingNews = newsRepository.findById(id);
        if (existingNews.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy tin tức cần cập nhật.");
            return "redirect:/dashboard/news";
        }

        if (bindingResult.hasErrors()) {
            populateNewsPage(model, principal, newsForm, id);
            return "dashboard/news";
        }

        News news = existingNews.get();
        applyNewsForm(news, newsForm, principal, false);
        newsRepository.save(news);

        redirectAttributes.addFlashAttribute("successMessage", "Đã cập nhật tin tức thành công.");
        return "redirect:/dashboard/news";
    }

    @PostMapping("/news/{id}/delete")
    public String deleteNews(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        if (!newsRepository.existsById(id)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy tin tức cần xóa.");
            return "redirect:/dashboard/news";
        }

        newsRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Đã xóa tin tức thành công.");
        return "redirect:/dashboard/news";
    }

    @GetMapping("/characters")
    public String characters(Model model, Principal principal) {
        CharacterForm characterForm = new CharacterForm();
        populateCharactersPage(model, principal, characterForm, null);
        return "dashboard/charactersPage";
    }

    @GetMapping("/characters/{id}/edit")
    public String editCharacter(@PathVariable Integer id, Model model, Principal principal,
                                RedirectAttributes redirectAttributes) {
        Optional<Characters> existingCharacter = charactersRepository.findById(id);
        if (existingCharacter.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy nhân vật cần sửa.");
            return "redirect:/dashboard/characters";
        }

        populateCharactersPage(model, principal, toCharacterForm(existingCharacter.get()), id);
        return "dashboard/charactersPage";
    }

    @PostMapping("/characters")
    public String createCharacter(@Valid @ModelAttribute("characterForm") CharacterForm characterForm,
                                  BindingResult bindingResult,
                                  Model model,
                                  Principal principal,
        RedirectAttributes redirectAttributes) {
        if (characterForm.getId() != null && charactersRepository.existsById(characterForm.getId())) {
            bindingResult.rejectValue("id", "duplicate", "ID nhân vật đã tồn tại");
        }

        Optional<Media> media = Optional.empty();
        if (characterForm.getMediaId() != null) {
            media = mediaRepository.findById(characterForm.getMediaId());
        }
        if (characterForm.getMediaId() != null && media.isEmpty()) {
            bindingResult.rejectValue("mediaId", "notFound", "Media được chọn không tồn tại");
        }

        if (bindingResult.hasErrors()) {
            populateCharactersPage(model, principal, characterForm, null);
            return "dashboard/charactersPage";
        }

        Characters character = new Characters();
        applyCharacterForm(character, characterForm, media.get());
        charactersRepository.save(character);

        redirectAttributes.addFlashAttribute("successMessage", "Đã thêm nhân vật thành công.");
        return "redirect:/dashboard/characters";
    }

    @PostMapping("/characters/{id}")
    public String updateCharacter(@PathVariable Integer id,
                                  @Valid @ModelAttribute("characterForm") CharacterForm characterForm,
                                  BindingResult bindingResult,
                                  Model model,
                                  Principal principal,
                                  RedirectAttributes redirectAttributes) {
        Optional<Characters> existingCharacter = charactersRepository.findById(id);
        if (existingCharacter.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy nhân vật cần cập nhật.");
            return "redirect:/dashboard/characters";
        }

        Optional<Media> media = Optional.empty();
        if (characterForm.getMediaId() != null) {
            media = mediaRepository.findById(characterForm.getMediaId());
        }
        if (characterForm.getMediaId() != null && media.isEmpty()) {
            bindingResult.rejectValue("mediaId", "notFound", "Media được chọn không tồn tại");
        }

        if (bindingResult.hasErrors()) {
            populateCharactersPage(model, principal, characterForm, id);
            return "dashboard/charactersPage";
        }

        Characters character = existingCharacter.get();
        characterForm.setId(id);
        applyCharacterForm(character, characterForm, media.get());
        charactersRepository.save(character);

        redirectAttributes.addFlashAttribute("successMessage", "Đã cập nhật nhân vật thành công.");
        return "redirect:/dashboard/characters";
    }

    @PostMapping("/characters/{id}/delete")
    public String deleteCharacter(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        if (!charactersRepository.existsById(id)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy nhân vật cần xóa.");
            return "redirect:/dashboard/characters";
        }

        charactersRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Đã xóa nhân vật thành công.");
        return "redirect:/dashboard/characters";
    }

    @GetMapping("/rankings")
    public String rankings(Model model, Principal principal) {
        addCommonModel(model, principal);
        return "dashboard/rankingsPage";
    }

    @GetMapping("/media")
    public String media(Model model, Principal principal) {
        MediaForm mediaForm = new MediaForm();
        mediaForm.setType("image");
        mediaForm.setEntityType("banner");
        mediaForm.setEntityId(1);
        populateMediaPage(model, principal, mediaForm, null);
        return "dashboard/mediaPage";
    }

    @GetMapping("/media/{id}/edit")
    public String editMedia(@PathVariable Integer id, Model model, Principal principal,
                            RedirectAttributes redirectAttributes) {
        Optional<Media> existingMedia = mediaRepository.findById(id);
        if (existingMedia.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy media cần sửa.");
            return "redirect:/dashboard/media";
        }

        populateMediaPage(model, principal, toMediaForm(existingMedia.get()), id);
        return "dashboard/mediaPage";
    }

    @PostMapping("/media")
    public String createMedia(@Valid @ModelAttribute("mediaForm") MediaForm mediaForm,
                              BindingResult bindingResult,
                              Model model,
                              Principal principal,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            populateMediaPage(model, principal, mediaForm, null);
            return "dashboard/mediaPage";
        }

        Media media = new Media();
        applyMediaForm(media, mediaForm);
        mediaRepository.save(media);

        redirectAttributes.addFlashAttribute("successMessage", "Đã thêm media thành công.");
        return "redirect:/dashboard/media";
    }

    @PostMapping("/media/{id}")
    public String updateMedia(@PathVariable Integer id,
                              @Valid @ModelAttribute("mediaForm") MediaForm mediaForm,
                              BindingResult bindingResult,
                              Model model,
                              Principal principal,
                              RedirectAttributes redirectAttributes) {
        Optional<Media> existingMedia = mediaRepository.findById(id);
        if (existingMedia.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy media cần cập nhật.");
            return "redirect:/dashboard/media";
        }

        if (bindingResult.hasErrors()) {
            populateMediaPage(model, principal, mediaForm, id);
            return "dashboard/mediaPage";
        }

        Media media = existingMedia.get();
        applyMediaForm(media, mediaForm);
        mediaRepository.save(media);

        redirectAttributes.addFlashAttribute("successMessage", "Đã cập nhật media thành công.");
        return "redirect:/dashboard/media";
    }

    @PostMapping("/media/{id}/delete")
    public String deleteMedia(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        if (!mediaRepository.existsById(id)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy media cần xóa.");
            return "redirect:/dashboard/media";
        }

        mediaRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Đã xóa media thành công.");
        return "redirect:/dashboard/media";
    }

    @GetMapping("/theme")
    public String theme(Model model, Principal principal) {
        addCommonModel(model, principal);
        return "dashboard/themePage";
    }
}
