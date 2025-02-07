class StorageService {
    static TOKEN = "token";
    static USER = "user";
  
    static saveToken(token) {
  
      window.localStorage.removeItem(this.TOKEN);
      window.localStorage.setItem(this.TOKEN, token);
    
    }
  
    static saveUser(user) {
      console.log(user)
      window.localStorage.removeItem(this.USER);
      window.localStorage.setItem(this.USER, JSON.stringify(user));
    }
  
    static getToken() {
     
      return window.localStorage.getItem(this.TOKEN);
    }
  
    static getUser() {
      const userJson = window.localStorage.getItem(this.USER);
      
      return userJson ? JSON.parse(userJson) : null;
    }
  
    static getUserRole() {
      const user = this.getUser();
      // console.log(user)
      return user ? user.role : "";
    }
  
    static getUserId() {
      const user = this.getUser();
      return user ? user.id : "";
    }
  
    static getUserName() {
      const user = this.getUser();
      return user ? user.name : "";
    }
  
    static getUserPhone() {
      const user = this.getUser();
      return user ? user.phone : "";
    }
  
    static getUserEmail() {
      const user = this.getUser();
      return user ? user.email : "";
    }
  
    static isAdminLoggedIn() {
      if (!this.getToken()) return false;
      return this.getUserRole() === "ADMIN";
    }
  
    static isCustomerLoggedIn() {
      if (!this.getToken()) return false;
      return this.getUserRole() === "CUSTOMER";
    }
  
    static logout() {
      window.localStorage.removeItem(this.TOKEN);
      window.localStorage.removeItem(this.USER);
    }
  }
  
  export default StorageService;