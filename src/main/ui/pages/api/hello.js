// Next.js API route support: https://nextjs.org/docs/api-routes/introduction

export default function handler(req, res) {

  //Example API Requests

  let host = "http://localhost:8080";

  let findAllAccounts = () => {
    return fetch(host + '/accounts')
        .then(x => x.json());
  };

  let saveLogin = (login) => {
    let createURL = "/create/"+login.email+"/"+login.password+"/false";

    return fetch(host + createURL, {
      method: 'POST',
    }).then(response =>
    {
      if (response.status == 200 || response.status == 201) return response.json();
      return null;
    })
        .then(id => id)
        .catch(error => {
          console.log(error);
          return null;
        });
  }

  //Example API Requests

}
