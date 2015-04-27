// __     ___
// \ \   / (_) _____      __
//  \ \ / /| |/ _ \ \ /\ / /
//   \ V / | |  __/\ V  V /
//    \_/  |_|\___| \_/\_/
Mustache.tags = ['[[', ']]'];
var showEvents = function(events) {
  var grid = $('#grid');
  var eventTemplate = $('#eventTemplate').html();
  
  var items = events.map(function(e) {
    return Mustache.render(eventTemplate, e);
  });

  grid.append(items);
  initGrid(grid.selector);
};

/**
 * Uses Wookmark.js for a Pinterest-style staggered grid
 * @param gridSelector jQuery-style selector for the grid element
 */
var initGrid = function(gridSelector) {
  // Wookmark options (see http://www.wookmark.com/jquery-plugin)
  var options = {
    align: 'center',
    autoResize: true,
    direction: 'left',
    ignoreInactiveItems: true,
    onLayoutChanged: undefined,
    resizeDelay: 50,

    offset: 2,
    verticalOffset: 2,
    outerOffset: 15,
    // TODO: base tile width on image width
    itemWidth: 300,
    flexibleWidth: true,
    fillEmptySpace: true,
    // Custom sorting function
    comparator: null,
    // Custom filtering functions
    possibleFilters: []
  };

  var w = new Wookmark(gridSelector, options);
  w.onResize();
  return w;
};

//     _    ____ ___   _                     _ _ _
//    / \  |  _ \_ _| | |__   __ _ _ __   __| | (_)_ __   __ _
//   / _ \ | |_) | |  | '_ \ / _` | '_ \ / _` | | | '_ \ / _` |
//  / ___ \|  __/| |  | | | | (_| | | | | (_| | | | | | | (_| |
// /_/   \_\_|  |___| |_| |_|\__,_|_| |_|\__,_|_|_|_| |_|\__, |
//                                                       |___/
var apiHost = 'http://dfournier.ovh/api';

/**
 * Error handling for failed API calls
 */
var onFailure = function() {
  // TODO
  console.log('Request to API failed.');
};

/**
 * Wrapper for requests to the API
 */
var getEndpoint = function(endpoint) {
  return function() {
    return $.ajax({
      url: apiHost + endpoint,
      dataType: 'JSON',
      crossOrigin: true,
      failure: onFailure
    });
  };
};

//var getEvents = getEndpoint('/event/');
// Mocked while CORS are not available
var getEvents = function() {
  var results = {
    "count": 3,
    "next": null,
    "previous": null,
    "results": [
    {
      "id": 1,
      "name": "24H de l'INSA",
      "description": "Concert trop cool !",
      "url": "https://www.24heures.org/",
      "startTime": "17:00:00",
      "endTime": "03:00:00",
      "startDate": "2015-05-22",
      "endDate": "2015-05-24",
      "price": "11.00",
      "min_age": 0,
      "address": "11 Avenue Albert Einstein",
      "city": {
        "name": "VILLEURBANNE",
        "ZIPcode": "69100"
      },
      "latitude": "45.7825684",
      "longitude": "4.8791668",
      "categories": [
      {
        "id": 1,
        "name": "Spectacle"
      }
      ],
      "tags": []
    },
    {
      "id": 2,
      "name": "Soprano",
      "description": "Concert de Soprano au Zenith de Saint Etienne",
      "url": "http://www.zenith-saint-etienne.fr/?numYear=&numMonth=04&idArtiste=415",
      "startTime": "20:00:00",
      "endTime": "23:01:00",
      "startDate": "2015-04-02",
      "endDate": "2015-04-02",
      "price": "29.00",
      "min_age": 0,
      "address": "Zenith",
      "city": {
        "name": "SAINT-ETIENNE",
        "ZIPcode": "42000"
      },
      "latitude": "45.4544190",
      "longitude": "4.3924270",
      "categories": [
      {
        "id": 2,
        "name": "Concert"
      }
      ],
      "tags": []
    },
    {
      "id": 3,
      "name": "Test Requete",
      "description": "Ceci est un test",
      "url": "http://127.0.0.1/",
      "startTime": "00:00:00",
      "endTime": "23:59:00",
      "startDate": "2015-01-01",
      "endDate": "2015-12-31",
      "price": "50.00",
      "min_age": 21,
      "address": "8 Place de Fourvière",
      "city": {
        "name": "LYON",
        "ZIPcode": "69005"
      },
      "latitude": "45.7619226",
      "longitude": "4.8226443",
      "categories": [
      {
        "id": 1,
        "name": "Spectacle"
      },
      {
        "id": 3,
        "name": "Pièce de Théâtre"
      },
      {
        "id": 4,
        "name": "Conférence"
      },
      {
        "id": 7,
        "name": "Soirée"
      }
      ],
      "tags": [
      {
        "id": 1,
        "name": "Concert"
      },
      {
        "id": 3,
        "name": "Rock"
      }
      ]
    }
    ]
  };

  return {
    done: function(cb) {
      cb(results);
    }
  };
};
var getCategories = getEndpoint('/category/');
var getTags = getEndpoint('/tag/');
var getCities = getEndpoint('/city/');


//  ___       _ _   _       _ _          _   _
// |_ _|_ __ (_) |_(_) __ _| (_)______ _| |_(_) ___  _ __
//  | || '_ \| | __| |/ _` | | |_  / _` | __| |/ _ \| '_ \
//  | || | | | | |_| | (_| | | |/ / (_| | |_| | (_) | | | |
// |___|_| |_|_|\__|_|\__,_|_|_/___\__,_|\__|_|\___/|_| |_|

/**
 * Function run on page load
 */
jQuery(document).ready(function($) {
  getEvents().done(function(res) {
    showEvents(res.results);
  });
});
