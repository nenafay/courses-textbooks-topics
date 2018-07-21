const xhr = new XMLHttpRequest()
xhr.onreadystatechange = function(){
	if (this.readyState == 4 && this.status == 200){
		// Get JSON from the returned string
		const res = JSON.parse(xhr.response)
		const container = document.querySelector('.container')

		console.log({res})

		res.forEach(function(course) {
			const courseItem = document.createElement('div')

			const name = document.createElement('h2')
			name.innerText = course.name;

			container.appendChild(courseItem)
			courseItem.appendChild(name);
		}
	}
}

xhr.open('GET', 'http://localhost:8080/courses', true)
xhr.send()//talks to rest controller so we don't have to refresh all the damn time.