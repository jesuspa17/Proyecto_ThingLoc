package com.salesianostriana.thinglocapp.thinglocapp.pojos.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jesus Pallares on 21/02/2016.
 */
public class ResultUsuario {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("groups")
        @Expose
        private List<Object> groups = new ArrayList<Object>();

        /**
         * No args constructor for use in serialization
         *
         */
        public ResultUsuario() {
        }

        /**
         *
         * @param id
         * @param username
         * @param email
         * @param groups
         * @param url
         */
        public ResultUsuario(Integer id, String url, String username, String email, List<Object> groups) {
            this.id = id;
            this.url = url;
            this.username = username;
            this.email = email;
            this.groups = groups;
        }

        /**
         *
         * @return
         * The id
         */
        public Integer getId() {
            return id;
        }

        /**
         *
         * @param id
         * The id
         */
        public void setId(Integer id) {
            this.id = id;
        }

        /**
         *
         * @return
         * The url
         */
        public String getUrl() {
            return url;
        }

        /**
         *
         * @param url
         * The url
         */
        public void setUrl(String url) {
            this.url = url;
        }

        /**
         *
         * @return
         * The username
         */
        public String getUsername() {
            return username;
        }

        /**
         *
         * @param username
         * The username
         */
        public void setUsername(String username) {
            this.username = username;
        }

        /**
         *
         * @return
         * The email
         */
        public String getEmail() {
            return email;
        }

        /**
         *
         * @param email
         * The email
         */
        public void setEmail(String email) {
            this.email = email;
        }

        /**
         *
         * @return
         * The groups
         */
        public List<Object> getGroups() {
            return groups;
        }

        /**
         *
         * @param groups
         * The groups
         */
        public void setGroups(List<Object> groups) {
            this.groups = groups;
        }

    }