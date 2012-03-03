package mirari.model.avatar

import org.springframework.web.multipart.MultipartFile

/**
 * @author alari
 * @since 12/13/11 5:10 PM
 */
interface AvatarHolder {
    Avatar getAvatar()

    void setAvatar(Avatar o)

    void setAvatarFile(File f)

    void setAvatarMultipartFile(MultipartFile f)
}
