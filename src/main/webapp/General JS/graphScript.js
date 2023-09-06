NUM_BALLS = 200;
CON_RADIUS = 90;

var canvas = document.getElementById("canvas");
canvas.width = window.innerWidth - 10;
canvas.height = window.innerHeight - 10;
canvas.style.position = 'absolute';
canvas.style.top = '0';
canvas.style.left = '0';
document.body.appendChild(canvas);
var ctx = canvas.getContext("2d");
var nodes = [];

function Node(x, y, radius, angle, color) {
    this.x = x;
    this.y = y;
    this.radius = radius;
    this.angle = angle;
    this.speed = 3;
    this.color = color;
}

function getRandomInt(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

function createNodes() {
    for (var k = 0; k < NUM_BALLS; k++) {
        var x = getRandomInt(10, canvas.width - 10);
        var y = getRandomInt(10, canvas.height - 10);
        var radius = 9;
        var angle = getRandomInt(0, 360);
        //var color = 'rgb(' + Math.random() * 255 + ', ' + Math.random() * 255 + ', ' + Math.random() * 255 + ')';
        var color = 'rgba(0,0,0,0.84)';
        nodes.push(new Node(x, y, radius, angle, color));
    }
}

function drawNodes() {
    for (var k = 0; k < nodes.length; k++) {
        var node = nodes[k];
        ctx.beginPath();
        ctx.arc(node.x, node.y, node.radius, 0, 2 * Math.PI);
        ctx.fillStyle = node.color;
        ctx.fill();
    }
}

function getDist(node1, node2) {
    return Math.sqrt(Math.pow((node1.x - node2.x), 2) + Math.pow((node1.y - node2.y), 2));
}

function isInside(node1, node2) {
    const distance = Math.sqrt(Math.pow((node1.x - node2.x), 2) + Math.pow((node1.y - node2.y), 2));
    return distance <= CON_RADIUS;
}

function drawEdges() {
    // ctx.strokeStyle = 'rgba(51,194,250,0.84)';
    ctx.lineWidth = 4;
    for (var k = 0; k < nodes.length; k++) {
        var node1 = nodes[k];
        for (var i = k + 1; i < nodes.length; i++) {
            var node2 = nodes[i];
            if (isInside(node1, node2)) {
                const scaledValue = (getDist(node1, node2)) / (70) * (1 - 0.25);
                ctx.strokeStyle = 'rgba(0,0,0,' + (1 - scaledValue) + ')';
                ctx.beginPath();
                ctx.moveTo(node1.x, node1.y);
                ctx.lineTo(node2.x, node2.y);
                ctx.stroke();
            }
        }
    }
}

function resetNode(node) {
    node.x = getRandomInt(10, canvas.width - 10);
    node.y = getRandomInt(10, canvas.height - 10);
    node.angle = getRandomInt(0, 360);
}

function updateNodePositions() {
    for (var k = 0; k < nodes.length; k++) {
        var node = nodes[k];
        var angleInRadians = (node.angle * Math.PI) / 180;
        var cosineValue = Math.cos(angleInRadians);
        var sineValue = Math.sin(angleInRadians);
        var dx = cosineValue * node.speed;
        var dy = sineValue * node.speed;
        if (node.x + dx <= -CON_RADIUS || node.x + dx >= canvas.width + CON_RADIUS)
            resetNode(node);
        else {
            node.x += dx;
        }
        if (node.y + dy <= -CON_RADIUS || node.y + dy >= canvas.height + CON_RADIUS)
            resetNode(node);
        else {
            node.y += dy;
        }

    }
}

function loop() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    drawEdges();
    drawNodes();
    updateNodePositions();
    requestAnimationFrame(loop);
}

createNodes();

loop();

function resizeCanvas() {
    canvas.width = window.innerWidth - 10;
    canvas.height = window.innerHeight - 10;
}

function handleClick(event) {
    var x = event.clientX - canvas.offsetLeft;
    var y = event.clientY - canvas.offsetTop;
    var angle = getRandomInt(0, 360);
    var index = getRandomInt(0, nodes.length - 1);
    nodes[index].x = x;
    nodes[index].y = y;
    nodes[index].angle = angle;
}

canvas.addEventListener("click", handleClick);
window.addEventListener('resize', resizeCanvas);
