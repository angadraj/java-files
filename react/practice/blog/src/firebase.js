// import { initializeApp } from "firebase/app";
import firebase from 'firebase/compat/app';
import 'firebase/compat/auth';
import 'firebase/compat/storage';
import 'firebase/compat/firestore';
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
    apiKey: "AIzaSyDfw7k-Wd2PucL6O2yMKIQvIL6TMNRBItg",
    authDomain: "blog-7db11.firebaseapp.com",
    projectId: "blog-7db11",
    storageBucket: "blog-7db11.appspot.com",
    messagingSenderId: "739175996835",
    appId: "1:739175996835:web:ac24950bca7ce2b2c34e16"
  };

// Initialize Firebase
firebase.initializeApp(firebaseConfig);

export const auth = firebase.auth();