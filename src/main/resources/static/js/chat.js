const userName = prompt("사용자 이름 입력:")
const roomId = document.getElementById('roomId').value

const sockJs = new SockJS('/chat')
const stomp = Stomp.over(sockJs)

window.onload = () => {
    stomp.connect({}, () => {
        console.log('STOMP Connection')

        stomp.subscribe(`/sub/room/${roomId}`, (content) => {
            const subscribeMessage = JSON.parse(content.body)
            const chat = {
                message: `${subscribeMessage.userName}님이 입장하였습니다.`,
                isSystemMessage: true
            }
            addMessage(chat)
        })

        stomp.subscribe(`/sub/room/${roomId}/chat`, (content) => {
            const subscribeMessage = JSON.parse(content.body)
            const chat = {
                message: subscribeMessage.message,
                isMyMessage: subscribeMessage.userName == userName,
                writer: subscribeMessage.userName
            }
            addMessage(chat)
        })

        const enterMessage = {
            roomId: roomId,
            userName: userName
        }
        stomp.send('/pub/room', {}, JSON.stringify(enterMessage))
    })
}

document.getElementById('sendButton').addEventListener('click', () => {
    const message = document.getElementById('chatInput').value
    if (message.trim() !== '') {
        const enterMessage = {
            roomId: roomId,
            userName: userName,
            message: message
        }
        stomp.send('/pub/room/chat', {}, JSON.stringify(enterMessage))
        document.getElementById('chatInput').value = ''
    }
})

document.getElementById('chatInput').addEventListener('keypress', (e) => {
    if (e.key === 'Enter') {
        document.getElementById('sendButton').click()
    }
})

const addMessage = ({ message = '', isMyMessage = false, writer = '', isSystemMessage = false }) => {
    const messageContainer = document.createElement('div')
    messageContainer.classList.add('chat-message')
    if (isSystemMessage) {
        messageContainer.classList.add('system-message')
    } else if (isMyMessage) {
        messageContainer.classList.add('my-message')
    } else {
        messageContainer.classList.add('other-message')
    }

    if (!isSystemMessage) {
        const usernameElement = document.createElement('div')
        usernameElement.classList.add('user-name')
        usernameElement.innerText = writer
        messageContainer.appendChild(usernameElement)
    }

    const messageText = document.createElement('p')
    messageText.innerText = message
    messageContainer.appendChild(messageText)
    document.getElementById('chatMessages').appendChild(messageContainer)
    document.getElementById('chatMessages').scrollTop = document.getElementById('chatMessages').scrollHeight
}