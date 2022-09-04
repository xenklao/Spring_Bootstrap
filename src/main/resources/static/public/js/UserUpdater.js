function RoleObject(uid, rid, role) {
    return {
        uid: uid,
        rid: rid,
        role: role
    }
}

function UserUpdater() {
}

// CRUD functions block start
UserUpdater.prototype.add = async () => {

    try {
        if (UserUpdater.prototype.checkNewUserForm() === false) {
            let _csrf = UserUpdater.prototype.getCSRF()
            let opt = $("#nus_selectRole option:selected")
            let roleId = $(opt).attr("data-id")
            let roleName = $(opt).val()

            await fetch("http://localhost:8080/addUser", {
                method: "POST",
                body: JSON.stringify(UserUpdater.prototype.createUserObjectNoId()),
                headers: new Headers([
                    ["Content-Type", "application/json;charset=UTF-8"],
                    [`${_csrf.header}`, `${_csrf.token}`]
                ])
            }).then(async response => {
                let responseData = (await response.json())
                UserUpdater.prototype.createNewRow(responseData)
                await UserUpdater.prototype.addRole(new RoleObject(
                    responseData.id, roleId, roleName
                ))
            })
        }
    } catch (e) {
        console.error(e)
    }


}

UserUpdater.prototype.updateUser = async (user) => {
    try {
        let _csrf = UserUpdater.prototype.getCSRF()
        let opt = $("#selectRoles option:selected")
        let roleId = $(opt).attr("data-id")
        let roleName = $(opt).val()

        await fetch("http://localhost:8080/updateUser", {
            method: "PUT",
            body: JSON.stringify(UserUpdater.prototype.updateUserObject(user)),
            headers: new Headers([
                ["Content-Type", "application/json;charset=UTF-8"],
                [`${_csrf.header}`, `${_csrf.token}`]
            ])
        }).then(async response => {
            let responseData = (await response.json())
            UserUpdater.prototype.refreshRowData(responseData)
            await UserUpdater.prototype.addRole(new RoleObject(
                user.id, roleId, roleName
            ))
        })

    } catch (e) {
        console.error(e)
    }
}

UserUpdater.prototype.deleteUser = async (userId) => {
    try {
        let _csrf = UserUpdater.prototype.getCSRF()
        await fetch("http://localhost:8080/removeUser", {
            method: "DELETE",
            body: JSON.stringify(UserUpdater.prototype.createUserObject(userId)),
            headers: [
                ["Content-Type", "application/json;charset=UTF-8"],
                [`${_csrf.header}`, `${_csrf.token}`]
            ]
        }).then(() => UserUpdater.prototype.deleteRow(userId))
    } catch (e) {
        console.error(e)
    }
}

UserUpdater.prototype.addRole = async (roleObj) => {
    try {
        let _csrf = UserUpdater.prototype.getCSRF()

        await fetch("http://localhost:8080/addRole", {
            method: "POST",
            body: JSON.stringify(roleObj),
            headers: [
                ["Content-Type", "application/json;charset=UTF-8"],
                [`${_csrf.header}`, `${_csrf.token}`]
            ]
        }).then(async r => {
            console.log(r.status)
            let result = await r.json()
            console.log(result)
            if (result.flag === true) {
                $(`tr[data-id=${roleObj.uid}]`)
                    .find("#utRoles")
                    .append(`<option data-id=${roleObj.rid} 
                                value=${roleObj.role}
                                >${roleObj.role}
                                </option>`)
            }
        })
    } catch (e) {
        console.error(e)
    }
}

//CRUD functions block end

//Secondary functions block start

UserUpdater.prototype.createUserObjectNoId = () => {
    let stackData = $("#newUserStack")
    return {
        firstName: $(stackData).find("#nus_firstName").val(),
        lastName: $(stackData).find("#nus_lastName").val(),
        age: $(stackData).find("#nus_age").val(),
        nickName: $(stackData).find("#nus_email").val(),
        password: $(stackData).find("#nus_password").val()
    }
}

UserUpdater.prototype.createUserObject = (userId) => {
    let row = document.querySelector(`#usersTable tbody tr[data-id='${userId}']`)
//TODO nickName will be email
    return {
        id: $(row).attr("data-id"),
        nickName: $(row).attr("data-nickname"),
        firstName: $(row).attr("data-firstName"),
        lastName: $(row).attr("data-lastName"),
        password: $(row).attr("data-password"),
        age: $(row).attr("data-age")
    }

}

UserUpdater.prototype.updateUserObject = (user) => {
    user.id = $("#modalInputId").val()
    user.nickName = $("#modalInputEmail").val()
    user.firstName = $("#modalInputFirstName").val()
    user.lastName = $("#modalInputLastName").val()
    user.password = $("#modalInputPassword").val()
    user.age = $("#modalInputAge").val()
    return user
}

UserUpdater.prototype.refreshRowData = (res) => {
    console.log(res)
    // console.log(res.roles[0].allRoles.role);

    let row = $(`tr[data-id=${res.id}]`)
    $(row).find("#tdId").text(res.id)
    $(row).find("#utFirstName").text(res.firstName)
    $(row).find("#utLastName").text(res.lastName)
    $(row).find("#utPassword").text(res.password)
    $(row).find("#utAge").text(res.age)
    $(row).find("#utNickName").text(res.nickName)

    $(row).attr("data-id", res.id)
    $(row).attr("data-firstName", res.firstName)
    $(row).attr("data-lastName", res.lastName)
    $(row).attr("data-password", res.password)
    $(row).attr("data-age", res.age)
    $(row).attr("data-nickname", res.nickName)
}

UserUpdater.prototype.deleteRow = (userId) => {
    let row = document.querySelector(`#usersTable tbody tr[data-id='${userId}']`)
    row.parentElement.removeChild(row)

}


UserUpdater.prototype.createNewRow = (user) => {
    $("#usersTable tbody").append(
        `<tr scope="row" 
                     data-id=${user.id}
                     data-firstName=${user.firstName}
                     data-lastName=${user.lastName}
                     data-password=${user.password}
                     data-age=${user.age}
                     data-nickname=${user.nickName}
                     data-roles=''>
                     
            <td id="utId">${user.id}</td>
            <td id="utFirstName">${user.firstName}</td>
            <td id="utLastName">${user.lastName}</td>
            <td id="utPassword">${user.password}</td>
            <td id="utAge">${user.age}</td>
            <td id="utNickName">${user.nickName}</td>
            <td id="utRoles">
            </td>
            <td>
                <button
                        type="button"
                        class="btn btn-info"
                        onclick="new UserUpdater().modalUserInfoEdit('${user.id}')"
                        data-bs-toggle="modal"
                        data-bs-target="#modalUserInfo">
                    Edit
                </button>
            </td>
            <td>
                <button type="button"
                        onclick="new UserUpdater().modalUserInfoDelete('${user.id}')"
                        class="btn btn-danger"
                        data-bs-toggle="modal"
                        data-bs-target="#modalUserInfo">
                    Delete
                </button>
            </td>

        </tr>`
    )
}

UserUpdater.prototype.getCSRF = () => {
    return {
        header: document.querySelector(`meta[name=_csrf_h]`).getAttribute("content"),
        token: document.querySelector(`meta[name=_csrf]`).getAttribute("content")
    }
}

UserUpdater.prototype.checkNewUserForm = () => {
    let isEmpty = false;
    $("#newUserStack input").each(function () {
        let elt = $(this)
        if (elt.val() === "") {
            isEmpty = true
        }
    })
    return isEmpty
}

//Secondary functions block end

//Modal
UserUpdater.prototype.modalUserInfoEdit = (userId) => {
    let user = UserUpdater.prototype.createUserObject(userId)
    let modalActionButton = $("#modal-footer__actionButtonName")

    $("#modalUserInfoLabel").text("Edit user")
    $(modalActionButton).removeClass('btn-danger')
    $(modalActionButton).addClass('btn-primary')
    $(modalActionButton).text("Edit")

    UserUpdater.prototype.setModalUserInfoBody(user)

    $(modalActionButton).unbind()

    $(modalActionButton).click(() => UserUpdater.prototype.updateUser(user))
}

UserUpdater.prototype.modalUserInfoDelete = (userId) => {
    let user = UserUpdater.prototype.createUserObject(userId)
    let modalActionButton = $("#modal-footer__actionButtonName")
    let selectRoles = $("#selectRoles")
    $("#modalUserInfoLabel").text("Delete user")
    $(modalActionButton).removeClass('btn-primary')
    $(modalActionButton).addClass('btn-danger')
    $(modalActionButton).text("Delete")

    $(modalActionButton).unbind()

    $(modalActionButton).click(() => UserUpdater.prototype.deleteUser(userId))

    UserUpdater.prototype.setModalUserInfoBody(user, "Delete user")

    $(selectRoles).empty()
    $(`tr[data-id=${userId}] td#utRoles option`).clone().appendTo($(selectRoles))
    UserUpdater.prototype.setReadOnlyModalBody()
}

UserUpdater.prototype.setReadOnlyModalBody = () => {
    let modalBody = $("#modalUserInfoBody")
    $(modalBody).find("input").each(function () {
        $(this).prop('readonly', true)
    })

    $(modalBody).find("select").each(function () {
        $(this).prop('disabled', true)
    })
}

UserUpdater.prototype.setModalUserInfoBody = (user) => {
    let modalBody = $("#modalUserInfoBody")
    $(modalBody).empty()
    $(modalBody).append(`<div class="text-center fw-bold">ID</div>`)
    $(modalBody).append(`<input disabled id="modalInputId" class="form-control me-auto"  type="text" value="${user.id}">`)
    $(modalBody).append(`<div class="text-center fw-bold">First Name</div>`)
    $(modalBody).append(`<input id="modalInputFirstName" class="form-control me-auto"  type="text" value="${user.firstName}">`)
    $(modalBody).append(`<div class="text-center fw-bold">Last Name</div>`)
    $(modalBody).append(`<input id="modalInputLastName" class="form-control me-auto"  type="text" value="${user.lastName}">`)
    $(modalBody).append(`<div class="text-center fw-bold">Password</div>`)
    $(modalBody).append(`<input id="modalInputPassword" class="form-control me-auto"  type="text" value="${user.password}">`)
    $(modalBody).append(`<div class="text-center fw-bold">Age</div>`)
    $(modalBody).append(`<input id="modalInputAge" class="form-control me-auto"  type="text" value="${user.age}">`)
    $(modalBody).append(`<div class="text-center fw-bold">Email</div>`)
    $(modalBody).append(`<input id="modalInputEmail" class="form-control me-auto"  type="text" value="${user.nickName}">`)
    $(modalBody).append(`<div class="text-center fw-bold">Roles</div>`)
    $(modalBody).append(`<select id='selectRoles' size='4' required></select>`)
    $("#nus_selectRole option").clone().appendTo($("#selectRoles"))

}
