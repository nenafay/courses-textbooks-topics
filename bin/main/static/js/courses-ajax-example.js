const xhr = new XMLHttpRequest()
xhr.onreadystatechange = function(){
	if (this.readyState == 4 && this.status == 200){
		// Get JSON from the returned string
		const res = JSON.parse(xhr.response)
		const container = document.querySelector('.container')

		console.log({res})
	}
}

xhr.open('GET', 'http://localhost:8080/courses', true)
xhr.send()