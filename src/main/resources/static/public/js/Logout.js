function Logout() {
}

Logout.prototype.logout = () => {
    let cs = {
        header: document.querySelector(`meta[name=_csrf_h]`).getAttribute("content"),
        token: document.querySelector(`meta[name=_csrf]`).getAttribute("content")
    }

    let headers = new Headers();
    headers.append('Content-Type', 'multipart/form-data;charset=utf-8')
    headers.append(cs.header, cs.token)

    document.querySelector("div.logout-button")
        .addEventListener('click', () => {
            fetch("/logout", {
                method: "POST",
                headers: headers
            }).then(r => r.status === 200 ? location.assign("/login") : console.log(r))
        })
}

(() => {
    new Logout().logout()
})()