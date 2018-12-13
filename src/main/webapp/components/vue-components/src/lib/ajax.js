export function get(url, handleSuccess, handleError) {
    $.ajax({
        type: 'GET',
        url: url,
        dataType: 'json',
        success: function (reps) {
            if (handleSuccess) handleSuccess(reps)
        },
        error: function (err) {
            if (handleError) handleError(err)
        }
    })
}

export function post(url, data, handleSuccess, handleError) {
    var str = '';
    for (var key in data) {
        str += key + '=' + data[key] + '&';
    }
    $.ajax({
        type: 'POST',
        url: url,
        dataType: 'json',
        data: str,
        success: function (reps) {
            if (handleSuccess) handleSuccess(reps)
        },
        error: function (err) {
            if (handleError) handleError(err)
        }
    })
}

export function postJson(url, data, handleSuccess, handleError) {
    $.ajax({
        type: 'POST',
        url: url,
        dataType: 'json',
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function (reps) {
            if (handleSuccess) handleSuccess(reps)
        },
        error: function (err) {
            if (handleError) handleError(err)
        }
    })
}