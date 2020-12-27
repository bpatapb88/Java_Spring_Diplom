// Create a new directed graph
var g = new dagreD3.graphlib.Graph().setGraph({});

// function to shuffle the list...
function shuffle(a) {
    var j, x, i;
    for (i = a.length; i; i -= 1) {
        j = Math.floor(Math.random() * i);
        x = a[i - 1];
        a[i - 1] = a[j];
        a[j] = x;
    }
    return a;
}

// set Labels for edges
for(var i = 0; i< edgeList2.length; i++){
edgeList2[i].push({"label":"" + edgeList2[i][1]});
}

var svg = d3.select("svg"),
    inner = svg.select("g");

function render_graph(render) {

  var max_cnt = 100;
  var iter_cnt = 0;
  var optimalArray, best_result;
  while(max_cnt--) {
      var g = new dagreD3.graphlib.Graph().setGraph({});
      for(var i = 0; i < nodes.length; i++){
            g.setNode(nodes[i], { label: nodes[i], shape: shapes[i] });
      }

      // set edges... randomize the list
      var list = shuffle(edgeList2);
      if(!optimalArray) optimalArray = list;

      edgeList2.forEach((edge)=>{
         g.setEdge.apply(g, edge);
      })

      // Set the rankdir
      g.graph().rankdir = "LR";
      g.graph().nodesep = 60;

      render(inner, g);

      var nn = svg.select(".edgePaths");
      var paths = nn[0][0];
      var fc = paths.firstChild;
      var boxes = [];
      while(fc) {
         var path = fc.firstChild.getAttribute("d");
         var coords = path.split(/,|L/).map(function(c) {
             var n = c;
             if((c[0]=="M" || c[0]=="L")) n = c.substring(1);
             return parseFloat(n);
         })
         boxes.push({ left : coords[0], top : coords[1], right : coords[coords.length-2], bottom : coords[coords.length-1]});
         fc = fc.nextSibling;
      }
      var collisionCnt = 0;
      boxes.forEach( function(a) {
         // --> test for collisions against other nodes...
         boxes.forEach(function(b) {
             if(a==b) return;
             // test if outside
             if ( (a.right  < b.left) ||
                  (a.left   > b.right) ||
                  (a.top    > b.bottom) ||
                  (a.bottom < b.top) ) {

                  // test if inside
                  if(a.left >= b.left  && a.left <=b.right || a.right >= b.left  && a.right <=b.right) {
                     if(a.top <= b.top && a.top >= b.bottom) {
                        collisionCnt++;
                     }
                     if(a.bottom <= b.top && a.bottom >= b.bottom) {
                        collisionCnt++;
                     }
                  }
             } else {
                collisionCnt++;
             }
         })
      })
     if(collisionCnt==0) {
         optimalArray = list.slice();
         break;
     }
     if(typeof(best_result) == "undefined") {
        best_result = collisionCnt;
     } else {
        if(collisionCnt < best_result) {
            optimalArray = list.slice();
            best_result = collisionCnt;
        }
     }
     iter_cnt++;
  }

  // if no optimal was found just render what was found...
  if(best_result >= 0 ) {
      var g = new dagreD3.graphlib.Graph().setGraph({});
      for(var i = 0; i < nodes.length; i++){
                  g.setNode(nodes[i], { label: nodes[i], shape: shapes[i] });
            }
      edgeList2.forEach((edge)=>{
         g.setEdge.apply(g, edge);
      })
      g.graph().rankdir = "LR";
      g.graph().nodesep = 60;
      render(inner, g);
  }

  // Center the graph
  var initialScale = 0.75;
  zoom
    .translate([(svg.attr("width") - g.graph().width * initialScale) / 2, 20])
    .scale(initialScale)
    .event(svg);
  svg.attr('height', g.graph().height * initialScale + 40);

}

// Set up zoom support
var zoom = d3.behavior.zoom().on("zoom", function() {
      inner.attr("transform", "translate(" + d3.event.translate + ")" +
                                  "scale(" + d3.event.scale + ")");
    });
svg.call(zoom);

// Create the renderer
var render = new dagreD3.render();

render_graph(render);