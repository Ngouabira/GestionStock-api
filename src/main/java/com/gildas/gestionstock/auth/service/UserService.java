package com.gildas.gestionstock.auth.service;

import com.gildas.gestionstock.auth.entity.User;
import com.gildas.gestionstock.auth.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Transactional
public class UserService {

    UserRepository userRepository;
    PasswordEncoder encoder;

    public List<User> read() {

        return userRepository.findAll();
    }

    /**
     *
     * @param user User the user to create
     * @return void
     */
    public String create(User user) {

        User userByEmail = userRepository.findByUsername(user.getUsername());
        User userByPhone = userRepository.findByTelephone(user.getTelephone());

        if (userByEmail != null) {

            return "Cette adresse email existe déjà";
        } else if (userByPhone != null) {

            return "Ce numéro de téléphone existe déjà";
        } else {
            String encoderPassword = encoder.encode(user.getPassword());
            user.setPassword(encoderPassword);
            userRepository.save(user);

            return "ok";

        }

    }


    /**
     *
     * @param id int l'id de l'utilisateur à supprimer
     */
    public void delete(Integer id) {

        userRepository.deleteById(id);

    }

    /**
     *
     * @param id int : l'id de l'utlisateur à récupérer
     */
    public User getOne(Integer id) {

        return userRepository.getById(id);
    }

    /**
     *
     * @param username String : le username d'utilisateur de l'utilisateur à récupérer
     */
    public User getByUsername(String username) {

        return userRepository.findByUsername(username);
    }


    /**
     *
     * @param id int : l'id de l'utilisateur à modifier
     * @param photo MultipartFile : la photo de l'utilisateur à modifier
     */
    public void addProfilePicture(int id, String photo) {

        User user = userRepository.getById(id);
        user.setPhoto(photo);
        userRepository.save(user);
    }

    /**
     *
     * @param id  int : l'id de l'utilisateur à modifier
     * @param status String : le nouvel état de l'utilisateur à modifier
     */
    public void updateStatus(int id, String status) {

        User user = userRepository.getById(id);
        user.setStatus(status);
        userRepository.save(user);
    }

    /**
     *
     * @param u User : l'utilisateur à modifier
     * @return String : le message de retour
     */
    public String updateProfile(User u) {

        User user = userRepository.getById(u.getId());
        User userUsername = userRepository.findByUsername(u.getUsername());
        User userPhone = userRepository.findByTelephone(u.getTelephone());
        String response;

        if (userUsername != null && !Objects.equals(user.getUsername(), userUsername.getUsername())) {

            response = "Cette adresse email existe déjà";
        } else if (userPhone != null && !Objects.equals(user.getTelephone(), userPhone.getTelephone())) {

            response = "Ce numéro de téléphone existe déjà";
        } else {

                user.setNom(u.getNom());
                user.setPrenom(u.getPrenom());
                user.setTelephone(u.getTelephone());
                user.setUsername(u.getUsername());
                user.setGenre(u.getGenre());
                user.setDate_naissance(u.getDate_naissance());
                userRepository.save(user);
                response = "ok";

        }

        return response;

    }

    /**
     *
     * @param u User : l'utilisateur à modifier
     * @return String : le message de retour
     */
    public String updateUsername(User u) {

        User userByEmail = userRepository.findByUsername(u.getUsername());

        if (userByEmail != null) {

            return "Cette adresse email existe déjà";
        } else {

            User user = userRepository.getById(u.getId());
            user.setUsername(u.getUsername());
            userRepository.save(user);

            return "ok";

        }

    }

    /**
     *
     * @param u User : l'utilisateur à modifier
     * @return String : le message de retour
     */
    public String updateTelephone(User u) {

        User userByTelephone = userRepository.findByUsername(u.getTelephone());

        if (userByTelephone != null) {

            return "Ce numéro de téléphone existe déjà";
        } else {


            User user = userRepository.getById(u.getId());
            user.setTelephone(u.getTelephone());
            userRepository.save(user);

            return "ok";

        }

    }

    /**
     *
     * @param id int : l'id de l'utilisateur à modifier
     * @param oldPassword String : l'ancien mot de passe de l'utilisateur à modifier
     * @param newPassword String : le nouveau mot de passe de l'utilisateur à modifier
     * @return String : le message de retour
     */
    public String updatePassword(int id, String oldPassword, String newPassword) {

        User user = userRepository.getById(id);
        String password = encoder.encode(newPassword);
        boolean test = encoder.matches(oldPassword, user.getPassword());
        if (test) {
            user.setPassword(password);
            userRepository.save(user);
            return "ok";


        } else {
            return "L'ancien mot de passe n'est pas correct!";
        }


    }

    /**
     *
     * @param token String : le nouveau token de l'utilisateur à modifier
     * @param email String : l'email de l'utilisateur à récupérer
     * @return String : le message de retour
     */
    public String updateResetPasswordToken(String token, String email) {

        User user = userRepository.findByUsername(email);

        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);

            return "ok";
        } else {
            return "Utilisateur inexistant " + email;
        }
    }

    /**
     *
     * @param token String : le token de l'utilisateur à récupérer
     * @return User : l'utilisateur récupéré
     */
    public User getByResetPasswordToken(String token) {

        return userRepository.findByResetPasswordToken(token);
    }

    /**
     *
     * @param user User : l'utilisateur à modifier
     * @param newPassword String : le nouveau mot de passe de l'utilisateur à modifier
     */
    public void updateUserPassword(User user, String newPassword) {

        String encoderPassword = encoder.encode(newPassword);
        user.setPassword(encoderPassword);
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

    /**
     *
     * @param nom  String : le nom de l'utilisateur à récupérer
     * @param genre String : le genre  de l'utilisateur à récupérer
     * @param role String : le role de l'utilisateur à récupérer
     * @param page int : la page de résultat à récupérer
     * @return Page<User> : la page de résultat de la recherche
     */
    public Page<User> getUsers(String nom, String genre, String role, int page) {
        System.out.println(nom + genre);
		Pageable pageRequest = PageRequest.of(page, 10);
        return userRepository.findByNomLikeAndGenreLikeAndRole("%"+nom+"%", "%"+genre+"%", role, pageRequest);
    }


}
