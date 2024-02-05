#lang racket
(require 2htdp/image)

(define P1 (list 1 "red" empty (list empty)))
(define P2 (list 2 "blue" empty (list empty)))
(define P3 (list 3 "yellow" empty (list empty)))
(define P4 (list 4 "green" empty (list empty)))
(define P5 (list 5 "purple" empty (list empty)))
(define P6 (list 6 "orange" empty (list empty)))
(define (player_list) (list P1 P2 P3 P4 P5 P6))
(define troops 0)

(define MAX_PLAYERS 6)
(define MAX_CARDS 5)
(define num_of_players 2)
(define phase 0)
(define turn_of 0)
(define card? 0)

;; DEFINIÇÕES DOS PAÍSES
(define alaska (list "alaska" "gray" 1 (list "mackenzie" "vancouver" "vladivostok")))
(define mackenzie (list "mackenzie" "gray" 1 (list "alaska" "groenlandia" "ottawa" "vancouver")))
(define groenlandia (list "groenlandia" "gray" 1 (list "islandia" "labrador" "mackenzie")))
(define vancouver (list "vancouver" "gray" 1 (list "alaska" "california" "mackenzie" "ottawa")))
(define ottawa (list "ottawa" "gray" 1 (list "california" "labrador" "mackenzie" "nova_york" "vancouver")))
(define labrador (list "labrador" "gray" 1 (list "groenlandia" "nova_york" "ottawa")))
(define california (list "california" "gray" 1 (list "mexico" "nova_york" "vancouver")))
(define nova_york (list "nova_york" "gray" 1 (list "california" "labrador" "mexico" "ottawa")))
(define mexico (list "mexico" "gray" 1 (list "california" "nova_york" "venezuela")))
(define venezuela (list "venezuela" "gray" 1 (list "brasil" "mexico" "peru")))
(define peru (list "peru" "gray" 1 (list "argentina" "brasil" "venezuela")))
(define brasil (list "brasil" "gray" 1 (list "argelia" "argentina" "peru" "venezuela")))
(define argentina (list "argentina" "gray" 1 (list "brasil" "peru")))
(define islandia (list "islandia" "gray" 1 (list "groenlandia" "inglaterra")))
(define inglaterra (list "inglaterra" "gray" 1 (list "alemanha" "franca" "islandia")))
(define suecia (list "suecia" "gray" 1 (list "inglaterra" "moscou")))
(define moscou (list "moscou" "gray" 1 (list "aral" "omsk" "oriente_medio" "polonia" "suecia")))
(define polonia (list "polonia" "gray" 1 (list "alemanha" "egito" "franca" "moscou" "oriente_medio")))
(define alemanha (list "alemanha" "gray" 1 (list "franca" "inglaterra" "polonia")))
(define franca (list "franca" "gray" 1 (list "alemanha" "argelia" "egito" "inglaterra" "polonia")))
(define argelia (list "argelia" "gray" 1 (list "brasil" "congo" "egito" "franca" "sudao")))
(define egito (list "egito" "gray" 1 (list "argelia" "franca" "oriente_medio" "polonia" "sudao")))
(define sudao (list "sudao" "gray" 1 (list "africa_do_sul" "argelia" "congo" "egito" "madagascar")))
(define congo (list "congo" "gray" 1 (list "africa_do_sul" "argelia" "sudao")))
(define africa_do_sul (list "africa_do_sul" "gray" 1 (list "congo" "madagascar" "sudao")))
(define madagascar (list "madagascar" "gray" 1 (list "africa_do_sul" "sudao")))
(define oriente_medio (list "oriente_medio" "gray" 1 (list "aral" "egito" "india" "moscou" "polonia")))
(define aral (list "aral" "gray" 1 (list "china" "india" "moscou" "omsk" "oriente_medio")))
(define omsk (list "omsk" "gray" 1 (list "aral" "china" "dudinka" "mongolia")))
(define dudinka (list "dudinka" "gray" 1 (list "mongolia" "omsk" "tchita")))
(define siberia (list "siberia" "gray" 1 (list "dudinka" "tchita" "vladivostok")))
(define tchita (list "tchita" "gray" 1 (list "china" "dudinka" "mongolia" "siberia" "vladivostok")))
(define vladivostok (list "vladivostok" "gray" 1 (list "china" "japao" "siberia" "tchita")))
(define japao (list "japao" "gray" 1 (list "china" "vladivostok")))
(define china (list "china" "gray" 1 (list "aral" "india" "japao" "mongolia" "omsk" "tchita" "vietna" "vladivostok")))
(define mongolia (list "mongolia" "gray" 1 (list "china" "dudinka" "omsk" "tchita")))
(define india (list "india" "gray" 1 (list "aral" "china" "oriente_medio" "sumatra" "vietna")))
(define vietna (list "vietna" "gray" 1 (list "borneo" "china" "india")))
(define borneo (list "borneo" "gray" 1 (list "australia" "nova_guine" "vietna")))
(define nova_guine (list "nova_guine" "gray" 1 (list "australia" "borneo")))
(define australia (list "australia" "gray" 1 (list "borneo" "nova_guine" "sumatra")))
(define sumatra (list "sumatra" "gray" 1 (list "australia" "india")))
(define north_america (list alaska mackenzie groenlandia vancouver ottawa labrador california nova_york mexico))
(define south_america (list venezuela peru brasil argentina))
(define europe (list islandia inglaterra suecia moscou polonia alemanha franca))
(define africa (list argelia egito sudao congo africa_do_sul madagascar))
(define asia (list oriente_medio aral omsk dudinka siberia tchita vladivostok japao china mongolia india vietna))
(define oceania (list borneo nova_guine australia sumatra))
(define world (list north_america south_america europe africa asia oceania))

;; HELP
(define (help) (begin (displayln "(help-game) - Shows how to start and restart a game.")
                      (displayln "(help-player) - Shows how to advance the game state.")
                      (displayln "(help-deployment) - Show commands of the deployment phase.")
                      (displayln "(help-attack) - Show commands of the attack phase.")
                      (displayln "(help-reallocate) - Show commands of the reallocating phase.")))

(define (help-game)(begin (displayln "(game-reset) - Reinitializes the game.")
                          (displayln "(game-start n) - Starts a game with n-players.")
                          ))
                          
(define (help-player) (begin (displayln "(draw world) - Shows the world map.")
                           (displayln "(pass) - Pass the turn to the next player.")
                           (displayln "(next) - Advance to the next phase of your turn.")))

(define (help-attack)(displayln "(attack a b n) - Attacks the territory 'b' with 'n' ammount of troops from the territory 'a'."))

(define (help-reallocate) (displayln "(move a b n) - Moves 'n' ammount of troops from 'a' to 'b'."))

;;
(define (current_p)(cond
                     [(= turn_of 1) P1]
                     [(= turn_of 2) P2]
                     [(= turn_of 3) P3]
                     [(= turn_of 4) P4]
                     [(= turn_of 5) P5]
                     [(= turn_of 6) P6]
                     [else empty]))

;; Simula um dado.
(define (dice)(+ (random 6) 1))

;; INICIALIZAÇÃO
(define (game-reset)(begin (set! phase 0)(set! turn_of 0))(displayln "Game reinitializing...")
(set! alaska (list "alaska" "red" 1 (list "mackenzie" "vancouver" "vladivostok")))
(set! mackenzie (list "mackenzie" "blue" 1 (list "alaska" "groenlandia" "ottawa" "vancouver")))
(set! groenlandia (list "groenlandia" "yellow" 1 (list "islandia" "labrador" "mackenzie")))
(set! vancouver (list "vancouver" "green" 1 (list "alaska" "california" "mackenzie" "ottawa")))
(set! ottawa (list "ottawa" "red" 1 (list "california" "labrador" "mackenzie" "nova_york" "vancouver")))
(set! labrador (list "labrador" "blue" 1 (list "groenlandia" "nova_york" "ottawa")))
(set! california (list "california" "yellow" 1 (list "mexico" "nova_york" "vancouver")))
(set! nova_york (list "nova_york" "green" 1 (list "california" "labrador" "mexico" "ottawa")))
(set! mexico (list "mexico" "red" 1 (list "california" "nova_york" "venezuela")))
(set! venezuela (list "venezuela" "blue" 1 (list "brasil" "mexico" "peru")))
(set! peru (list "peru" "yellow" 1 (list "argentina" "brasil" "venezuela")))
(set! brasil (list "brasil" "green" 1 (list "argelia" "argentina" "peru" "venezuela")))
(set! argentina (list "argentina" "green" 1 (list "brasil" "peru")))
(set! islandia (list "islandia" "gray" 1 (list "groenlandia" "inglaterra")))
(set! inglaterra (list "inglaterra" "gray" 1 (list "alemanha" "franca" "islandia")))
(set! suecia (list "suecia" "gray" 1 (list "inglaterra" "moscou")))
(set! moscou (list "moscou" "gray" 1 (list "aral" "omsk" "oriente_medio" "polonia" "suecia")))
(set! polonia (list "polonia" "gray" 1 (list "alemanha" "egito" "franca" "moscou" "oriente_medio")))
(set! alemanha (list "alemanha" "gray" 1 (list "franca" "inglaterra" "polonia")))
(set! franca (list "franca" "gray" 1 (list "alemanha" "argelia" "egito" "inglaterra" "polonia")))
(set! argelia (list "argelia" "gray" 1 (list "brasil" "congo" "egito" "franca" "sudao")))
(set! egito (list "egito" "gray" 1 (list "argelia" "franca" "oriente_medio" "polonia" "sudao")))
(set! sudao (list "sudao" "gray" 1 (list "africa_do_sul" "argelia" "congo" "egito" "madagascar")))
(set! congo (list "congo" "gray" 1 (list "africa_do_sul" "argelia" "sudao")))
(set! africa_do_sul (list "africa_do_sul" "gray" 1 (list "congo" "madagascar" "sudao")))
(set! madagascar (list "madagascar" "gray" 1 (list "africa_do_sul" "sudao")))
(set! oriente_medio (list "oriente_medio" "gray" 1 (list "aral" "egito" "india" "moscou" "polonia")))
(set! aral (list "aral" "gray" 1 (list "china" "india" "moscou" "omsk" "oriente_medio")))
(set! omsk (list "omsk" "gray" 1 (list "aral" "china" "dudinka" "mongolia")))
(set! dudinka (list "dudinka" "gray" 1 (list "mongolia" "omsk" "tchita")))
(set! siberia (list "siberia" "gray" 1 (list "dudinka" "tchita" "vladivostok")))
(set! tchita (list "tchita" "gray" 1 (list "china" "dudinka" "mongolia" "siberia" "vladivostok")))
(set! vladivostok (list "vladivostok" "gray" 1 (list "china" "japao" "siberia" "tchita")))
(set! japao (list "japao" "gray" 1 (list "china" "vladivostok")))
(set! china (list "china" "gray" 1 (list "aral" "india" "japao" "mongolia" "omsk" "tchita" "vietna" "vladivostok")))
(set! mongolia (list "mongolia" "gray" 1 (list "china" "dudinka" "omsk" "tchita")))
(set! india (list "india" "gray" 1 (list "aral" "china" "oriente_medio" "sumatra" "vietna")))
(set! vietna (list "vietna" "gray" 1 (list "borneo" "china" "india")))
(set! borneo (list "borneo" "gray" 1 (list "australia" "nova_guine" "vietna")))
(set! nova_guine (list "nova_guine" "gray" 1 (list "australia" "borneo")))
(set! australia (list "australia" "gray" 1 (list "borneo" "nova_guine" "sumatra")))
(set! sumatra (list "sumatra" "gray" 1 (list "australia" "india")))
(set! north_america (list alaska mackenzie groenlandia vancouver ottawa labrador california nova_york mexico))
(set! south_america (list venezuela peru brasil argentina))
(set! europe (list islandia inglaterra suecia moscou polonia alemanha franca))
(set! africa (list argelia egito sudao congo africa_do_sul madagascar))
(set! asia (list oriente_medio aral omsk dudinka siberia tchita vladivostok japao china mongolia india vietna))
(set! oceania (list borneo nova_guine australia sumatra))
(set! world (list north_america south_america europe africa asia oceania))
  )

;; INICIA UM JOGO REINICIALIZADO
(define (game-start n)(if (and (>= n 3) (<= n 6))
                      (begin (displayln "Game starting...")
                             (set! num_of_players n)
                             (paint-map world)
                             (set! phase 0)(set! turn_of 1)
                             (display "Turn of Player ")(displayln turn_of)(displayln "Type (next) to start your actions.")
                             )
                      (displayln "You can only start a game with at least 3 and up to 6 people.")))

(define (paint-map lst)(cond [(empty? lst)(void)]
                             [else (begin (paint-country (first lst))(paint-map (rest lst)))]))
(define (paint-country lst)(cond [(empty? lst)(void)]
                                 [else (begin (recolor (first lst)(second (current_p)))
                                              (if (< turn_of num_of_players)
                                                  (set! turn_of (+ turn_of 1))
                                                  (set! turn_of 1))
                                              (paint-country (rest lst)))]))

;;AVANÇA PARA A PRÓXIMA FASE
(define (next)(cond
                [(= phase 0) (begin (set! phase (+ phase 1))(displayln "--DEPLOYMENT PHASE--")(update-troops))]
                [(= phase 1) (if (= troops 0)(begin (set! phase (+ phase 1))(displayln "--ATTACK PHASE--"))(displayln "There are not deployed troops."))]
                [(= phase 2) (begin (set! phase (+ phase 1))(displayln "--REALLOCATE PHASE--"))]
                [else   (displayln "You should use (pass) instead.")]))

;; PASSA O TURNO PARA O PRÓXIMO JOGADOR
(define (pass)(if (< turn_of num_of_players)
                  (begin (set! turn_of (+ turn_of 1))(set! phase 0)(display "Turn of Player ")(displayln turn_of)(displayln "Type (next) to start your actions."))
                  (begin (set! turn_of 1)(set! phase 0)(display "Turn of Player ")(displayln turn_of)(displayln "Type (next) to start your actions."))))

;; VERIFICA CONDIÇÃO DE VITÓRIA
(define (check-victory)(if (= (count_countries_owned (current_p)) 24)
                           (begin (display "THE PLAYER ")(display turn_of)(displayln " HAS WON!!!"))
                           (void)))

;; DEPLOYMENT PHASE

;; Concede tropas ao jogador
(define (update-troops) ((lambda (n)(begin (display "You got ")(display n)(displayln " troops this turn.")(set! troops n)))(gather-troops (current_p))))

(define (count_countries_owned p)(+ (foldr + 0 (map (lambda (own)(if own 1 0))(map (lambda (country)(equal? (second p)(second country)))(first world))))
                                    (foldr + 0 (map (lambda (own)(if own 1 0))(map (lambda (country)(equal? (second p)(second country)))(second world))))
                                    (foldr + 0 (map (lambda (own)(if own 1 0))(map (lambda (country)(equal? (second p)(second country)))(third world))))
                                    (foldr + 0 (map (lambda (own)(if own 1 0))(map (lambda (country)(equal? (second p)(second country)))(fourth world))))
                                    (foldr + 0 (map (lambda (own)(if own 1 0))(map (lambda (country)(equal? (second p)(second country)))(fifth world))))
                                    (foldr + 0 (map (lambda (own)(if own 1 0))(map (lambda (country)(equal? (second p)(second country)))(sixth world))))))

(define (gather-troops p)(floor (/ (count_countries_owned p) 2)))

;;(define (card-exchange card1 card2 card3) ...)

;; Deployment - Adiciona 'ammount' ao território 'country'.
(define (deploy country ammount)(if (and (< troops ammount)(not(= phase 1)))
                                    (displayln "Não possui unidades suficientes.")
                                    (begin (display ammount)(display " troops deployed. ")(display (- troops ammount))(displayln " remaining.")(set! troops (- troops ammount))(change_pop country ammount))))

;; Incrementa e decrementa a ocupação de um território.
(define (change_pop country ammount)(if (false? (cond
                                         [(equal? (first country) "alaska")(set! alaska (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "mackenzie")(set! mackenzie (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "roenlandia")(set! groenlandia (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "vancouver")(set! vancouver (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "ottawa")(set! ottawa (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "labrador")(set! labrador (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "california")(set! california (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "nova_york")(set! nova_york (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "mexico")(set! mexico (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "venezuela")(set! venezuela (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "peru")(set! peru (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "brasil")(set! brasil (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "argentina")(set! argentina (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "islandia")(set! islandia (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "inglaterra")(set! inglaterra (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "suecia")(set! suecia (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "moscou")(set! moscou (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "polonia")(set! polonia (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "alemanha")(set! alemanha (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "franca")(set! franca (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "argelia")(set! argelia (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "egito")(set! egito (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "sudao")(set! sudao (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "congo")(set! congo (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "africa_do_sul")(set! africa_do_sul (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "madagascar")(set! madagascar (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "oriente_medio")(set! oriente_medio (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "aral")(set! aral (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "omsk")(set! omsk (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "dudinka")(set! dudinka (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "siberia")(set! siberia (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "tchita")(set! tchita (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "vladivostok")(set! vladivostok (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "japao")(set! japao (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "china")(set! china (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "mongolia")(set! mongolia (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "india")(set! india (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "vietna")(set! vietna (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "borneo")(set! borneo (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "nova_guine")(set! nova_guine (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "australia")(set! australia (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [(equal? (first country) "sumatra")(set! sumatra (list (first country) (second country)(+ (third country) ammount)(fourth country)))]
                                         [else false]))
                                         (begin (displayln "This territory doesn't exist (in the board).")(displayln country))
                                         (update-world)))
  

;; ATTACK PHASE

;; Attack - realiza as ações de ataque.
(define (attack origin target num)(if (and
                                          (equal? (second (current_p))(second origin))
                                          (vizinho? (first origin) (fourth target))
                                          (not (equal? (second (current_p)) (second target)))
                                          (< num 4)
                                          (> num 0)
                                          (> (- (third origin) num) 0)
                                          (= phase 2))  
                                      (begin (cond
                                               [(= (third target) 1)(begin ((lambda(x)(if (equal? (first x) 1)
                                                                                          (begin (change_pop origin (- -1 (second x)))(recolor target (second (current_p))))                 ;;(change_pop target (- 0 (first x))
                                                                                          (begin (change_pop origin (- 0 (second x)))(change_pop target (- 0 (first x))))))(battle num 1)))]
                                               [(= (third target) 2)(begin ((lambda(x)(if (equal? (first x) 2)
                                                                                          (begin (change_pop origin (- -1 (second x)))(recolor target (second (current_p))));;(change_pop target (- 0 (first x))
                                                                                          (begin (change_pop origin (- 0 (second x)))(change_pop target (- 0 (first x))))))(battle num 2)))]
                                               [(= (third target) 3)(begin ((lambda(x)(if (equal? (first x) 3)
                                                                                          (begin (change_pop origin (- -1 (second x)))(recolor target (second (current_p))));;(change_pop target (- 0 (first x))
                                                                                          (begin (change_pop origin (- 0 (second x)))(change_pop target (- 0 (first x))))))(battle num 3)))]
                                               [(> (third target) 3)(begin ((lambda(x)(begin (change_pop origin (- 0 (second x)))(change_pop target (- 0 (first x)))))(battle num 3)))]
                                               [else (displayln "Error")])
                                             )
                                      (displayln "An error has ocurred.")))

(define (aux-attack origin target)(begin (change_pop origin (- 0 1))
                                         (change_pop target 1)
                                         (recolor target (second (current_p)))
                                         ))

;; Verifica se dois territórios são vizinhos.
(define (vizinho? c c_lst)(cond
                            [(empty? c_lst) #f]
                            [(equal? c (first c_lst)) #t]
                            [else (vizinho? c (rest c_lst))]))

;;Armazena o resultado dos dados e retorna o par de '(vitórias derrotas).
(define battle_results (list 0 0))
(define rolls_x '(0 0 0))
(define rolls_y '(0 0 0))
(define (roll-dices z)(cond
                        [(= z 3)(sort (list (+ (random 6) 1) (+ (random 6) 1) (+ (random 6) 1))>)]
                        [(= z 2)(sort (list 0 (+ (random 6) 1)  (+ (random 6) 1))>)]
                        [(= z 1)(sort (list 0 0 (+ (random 6) 1))>)]
                        [else (displayln "Erro.")]))
(define (battle x y)(begin (set! rolls_x (roll-dices x))(set! rolls_y (roll-dices y))(map (lambda (x y z)(+ x y z))(if (> (first rolls_x)(first rolls_y))
                                                                                                                       '(1 0)
                                                                                                                       '(0 1))
                                                                                                                   (if (and (not (zero? (second rolls_x)))
                                                                                                                            (not (zero? (second rolls_y))))
                                                                                                                       (if (> (second rolls_x)(second rolls_y))'(1 0) '(0 1))
                                                                                                                       '(0 0))
                                                                                                                   (if (and (not (zero? (third rolls_x)))
                                                                                                                            (not (zero? (third rolls_y))))
                                                                                                                       (if (> (third rolls_x)(third rolls_y))'(1 0) '(0 1))
                                                                                                                       '(0 0)) )))

;; Altera a cor de um território.
(define (recolor country color)(if (false? (cond
                                         [(equal? (first country) "alaska")(set! alaska (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "mackenzie")(set! mackenzie (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "groenlandia")(set! groenlandia (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "vancouver")(set! vancouver (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "ottawa")(set! ottawa (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "labrador")(set! labrador (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "california")(set! california (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "nova_york")(set! nova_york (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "mexico")(set! mexico (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "venezuela")(set! venezuela (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "peru")(set! peru (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "brasil")(set! brasil (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "argentina")(set! argentina (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "islandia")(set! islandia (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "inglaterra")(set! inglaterra (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "suecia")(set! suecia (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "moscou")(set! moscou (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "polonia")(set! polonia (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "alemanha")(set! alemanha (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "franca")(set! franca (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "argelia")(set! argelia (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "egito")(set! egito (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "sudao")(set! sudao (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "congo")(set! congo (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "africa_do_sul")(set! africa_do_sul (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "madagascar")(set! madagascar (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "oriente_medio")(set! oriente_medio (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "aral")(set! aral (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "omsk")(set! omsk (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "dudinka")(set! dudinka (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "siberia")(set! siberia (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "tchita")(set! tchita (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "vladivostok")(set! vladivostok (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "japao")(set! japao (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "china")(set! china (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "mongolia")(set! mongolia (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "india")(set! india (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "vietna")(set! vietna (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "borneo")(set! borneo (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "nova_guine")(set! nova_guine (list (first country) color (third country)(fourth country)))]
                                         [(equal? (first country) "australia")(set! australia (list (first country) color (third country) (fourth country)))]
                                         [(equal? (first country) "sumatra")(set! sumatra (list (first country) color (third country)(fourth country)))]
                                         [else false]))
                                         (displayln "This territory doesn't exist (in the board).")
                                         (begin (update-world)(check-victory))))



;; REALLOCATE PHASE
(define (move origin target num)(if (and
                                     (equal? (second (current_p))(second origin))
                                     (vizinho? (first origin) (fourth target))
                                     (equal? (second (current_p))(second target))
                                     (< num (third origin))
                                     (> num 0)
                                     (= phase 3))
                                    (begin (change_pop origin (- 0 num))(change_pop target num))
                                    (displayln "An error has ocurred.")))


;;WORLD
(define (draw-north lst) (above/align "left" (beside (line 50 0 "black")
                                                      (overlay (above (text (number->string (third (first lst))) 20 "black")(text (first (first lst)) 12 "black"))(rectangle 70 70 "solid" (second (first lst))))
                                                      (overlay (above (text (number->string (third (second lst))) 20 "black")(text (first (second lst)) 12 "black"))(rectangle 100 70 "solid" (second (second lst))))
                                                      (line 80 0 "black")
                                                      (overlay (above (text (number->string (third (third lst))) 20 "black")(text (first (third lst)) 12 "black"))(rectangle 70 70 "solid" (second (third lst)))))
                                              (beside/align "top" (rectangle 70 70 "solid" "white")
                                                                  (overlay (above (text (number->string (third (fourth lst))) 20 "black")(text (first (fourth lst)) 12 "black"))(rectangle 85 70 "solid" (second (fourth lst))))
                                                                  (overlay (above (text (number->string (third (fifth lst))) 20 "black")(text (first (fifth lst)) 12 "black"))(rectangle 70 70 "solid" (second (fifth lst))))
                                                                  (overlay (above (text (number->string (third (sixth lst))) 20 "black")(text (first (sixth lst)) 12 "black"))(rectangle 70 70 "solid" (second (sixth lst))))
                                                                  (line 30 -60 "black"))
                                              (beside (rectangle 100 70 "solid" "white")
                                                      (overlay (above (text (number->string (third (seventh lst))) 20 "black")(text (first (seventh lst)) 12 "black"))(rectangle 75 70 "solid" (second (seventh lst))))
                                                      (overlay (above (text (number->string (third (eighth lst))) 20 "black")(text (first (eighth lst)) 12 "black"))(rectangle 70 70 "solid" (second (eighth lst)))))
                                              (beside (rectangle 140 60 "solid" "white")
                                                      (overlay (above (text (number->string (third (ninth lst))) 20 "black")(text (first (ninth lst)) 12 "black"))(rectangle 50 60 "solid" (second (ninth lst)))))))

(define (draw-south lst) (above/align "left" (beside (rectangle 20 40 "solid" "white")(overlay (above (text (number->string (third (first lst))) 20 "black")(text (first (first lst)) 12 "black"))(rectangle 70 40 "solid" (second (first lst)))))
                                             (beside/align "top" (above (overlay (above (text (number->string (third (second lst))) 20 "black")(text (first (second lst)) 12 "black"))(rectangle 40 50 "solid" (second (second lst))))
                                                           (overlay (above (text (number->string (third (fourth lst))) 20 "black")(text (first (fourth lst)) 8 "black"))(rectangle 40 100 "solid" (second (fourth lst)))))
                                                    (overlay (above (text (number->string (third (third lst))) 20 "black")(text (first (third lst)) 12 "black"))(rectangle 70 100 "solid" (second (third lst))))
                                                    (line 120 -48 "black")
                                                    )))

(define (draw-europe lst)(beside/align "top" (beside (line 50 0 "black")(underlay/offset (line 40 40 "black") -20 -20 (overlay (above (text (number->string (third (first lst))) 20 "black")(text (first (first lst)) 12 "black"))(rectangle 50 40 "solid" (second (first lst))))))
                                             (beside/align "bottom" (above/align "right" (rectangle 0 50 "solid" "white")
                                                                                         (beside (overlay (above (text (number->string (third (second lst))) 20 "black")(text (first (second lst)) 12 "black"))(rectangle 55 40 "solid" (second (second lst))))
                                                                                                 (line 50 0 "black")
                                                                                                 (overlay (above (text (number->string (third (third lst))) 20 "black")(text (first (third lst)) 12 "black"))(rectangle 50 40 "solid" (second (third lst)))))
                                                                                         (beside/align "bottom" (above (line 5 60 "black")
                                                                                                                       (rectangle 0 25 "solid" "white"))
                                                                                                                (above/align "right" (beside (above (line 10 20 "black")
                                                                                                                                                    (rectangle 0 20 "solid" "white"))
                                                                                                                                             (overlay (above (text (number->string (third (sixth lst))) 20 "black")(text "alem" 12 "black"))(rectangle 50 40 "solid" (second (sixth lst)))))
                                                                                                                                     (overlay (above (text (number->string (third (seventh lst))) 20 "black")(text (first (seventh lst)) 12 "black"))(rectangle 90 40 "solid" (second (seventh lst)))))
                                                                                                                (overlay (above (text (number->string (third (fifth lst))) 20 "black")(text (first (fifth lst)) 12 "black"))(rectangle 50 60 "solid" (second (fifth lst))))))
                                                                    (overlay (above (text (number->string (third (fourth lst))) 20 "black")(text (first (fourth lst)) 12 "black"))(rectangle 50 100 "solid" (second (fourth lst)))))))


(define (draw-africa lst)(above/align "right" (beside (line 0 20 "black")(rectangle 20 20 "solid" "white")(line 2 20 "black")(rectangle 20 20 "solid" "white")(line -2 20 "black")(rectangle 20 20 "solid" "white"))
                                              (beside (above (rectangle 0 63 "solid" "white")(line -134 50 "black"))
                                                      (above/align "right" (overlay (above (text (number->string (third (first lst))) 20 "black")(text (first (first lst)) 12 "black"))(rectangle 70 70 "solid" (second (first lst))))
                                                                           (overlay (above (text (number->string (third (fourth lst))) 20 "black")(text (first (fourth lst)) 12 "black"))(rectangle 50 40 "solid" (second (fourth lst)))))
                                                      (above (overlay (above (text (number->string (third (second lst))) 20 "black")(text (first (second lst)) 12 "black"))(rectangle 50 40 "solid" (second (second lst))))
                                                             (overlay (above (text (number->string (third (third lst))) 20 "black")(text (first (third lst)) 12 "black"))(rectangle 50 70 "solid" (second (third lst))))))
                                              (beside/align "top" (underlay/offset (line 40 0 "black") -10 0 (overlay (above (text (number->string (third (fifth lst))) 20 "black")(text "a_do_sul" 12 "black"))(rectangle 50 70 "solid" (second (fifth lst)))))
                                                                  (overlay/offset (overlay (above (text (number->string (third (sixth lst))) 20 "black")(text "m" 12 "black"))(rectangle 20 20 "solid" (second (sixth lst)))) 0 -20 (line 0 25 "black")))))

(define (draw-asia-wo-om lst)(above/align "left" (beside/align "bottom" (above (overlay (above (text (number->string (third (second lst))) 20 "black")(text (first (second lst)) 12 "black"))(rectangle 60 70 "solid" (second (second lst))))
                                                                               (overlay (above (text (number->string (third (first lst))) 20 "black")(text (first (first lst)) 12 "black"))(rectangle 60 70 "solid" (second (first lst)))))
                                                                        (above/align "right" (overlay (above (text (number->string (third (fourth lst))) 20 "black")(text (first (fourth lst)) 12 "black"))(rectangle 110 60 "solid" (second (fourth lst))))
                                                                                             (beside (above (overlay (above (text (number->string (third (third lst))) 20 "black")(text (first (third lst)) 12 "black"))(rectangle 60 40 "solid" (second (third lst))))
                                                                                                            (overlay (above (text (number->string (third (ninth lst))) 20 "black")(text "mongol" 12 "black"))(rectangle 60 40 "solid" (second (ninth lst)))))
                                                                                                     (overlay (above (text (number->string (third (fifth lst))) 20 "black")(text (first (fifth lst)) 12 "black"))(rectangle 60 80 "solid" (second (fifth lst)))))
                                                                                             (overlay (above (text (number->string (third (eighth lst))) 20 "black")(text (first (eighth lst)) 12 "black"))(rectangle 120 60 "solid" (second (eighth lst)))))
                                                                        (beside (overlay (above (text (number->string (third (sixth lst))) 20 "black")(text (first (sixth lst)) 12 "black"))(rectangle 60 160 "solid" (second (sixth lst))))
                                                                                (line 60 0 "black")))
                                                 (beside (overlay (above (text (number->string (third (tenth lst))) 20 "black")(text (first (tenth lst)) 12 "black"))(rectangle 80 60 "solid" (second (tenth lst))))
                                                         (overlay (above (text (number->string (third (last lst))) 20 "black")(text (first (last lst)) 12 "black"))(rectangle 60 60 "solid" (second (last lst))))
                                                         (line 30 0 "black")
                                                         (underlay/offset (line 0 45 "black") 0 15 (overlay (above (text (number->string (third (seventh lst))) 20 "black")(text (first (seventh lst)) 12 "black"))(rectangle 30 30 "solid" (second (seventh lst))))))
                                    
                          )
                       )

(define (draw-oceania lst)(above/align "left" (beside (rectangle 10 30 "solid" "white")(line 15 30 "black")(rectangle 45 30 "solid" "white")(line 30 30 "black"))
                                              (above (beside (underlay/offset (line 60 20 "black") -25 -25 (overlay (above (text (number->string (third (fourth lst))) 20 "black")(text (first (fourth lst)) 12 "black"))(rectangle 30 60 "solid" (second (fourth lst)))))
                                                             (underlay/offset (line 30 0 "black") -20 10 (underlay/offset (line 30 50 "black") 0 -20 (overlay (above (text (number->string (third (first lst))) 20 "black")(text (first (first lst)) 12 "black"))(rectangle 40 40 "solid" (second (first lst))))))
                                                             (underlay/offset (line -70 50 "black") 0 -20 (overlay (above (text (number->string (third (second lst))) 20 "black")(text (first (second lst)) 12 "black"))(rectangle 70 40 "solid" (second (second lst))))))
                                                     (overlay (above (text (number->string (third (third lst))) 20 "black")(text (first (third lst)) 12 "black"))(rectangle 70 70 "solid" (second (third lst)))))))

(define (update-world)(begin
                        (set! north_america (list alaska mackenzie groenlandia vancouver ottawa labrador california nova_york mexico))
                        (set! south_america (list venezuela peru brasil argentina))
                        (set! europe (list islandia inglaterra suecia moscou polonia alemanha franca))
                        (set! africa (list argelia egito sudao congo africa_do_sul madagascar))
                        (set! asia (list oriente_medio aral omsk dudinka siberia tchita vladivostok japao china mongolia india vietna))
                        (set! oceania (list borneo nova_guine australia sumatra))
                        (set! world (list north_america south_america europe africa asia oceania))))

(define (draw-world)
(beside/align "top" (above/align "right" (draw-north (first world))
                                         (draw-south (second world)))
                    (above/align "right" (draw-europe (third world))
                                         (beside/align "top" (draw-africa (fourth world))(overlay (above (text (number->string (third oriente_medio)) 20 "black")(text "oriente_m" 12 "black"))(rectangle 70 50 "solid" (second oriente_medio)))))
                    (above (draw-asia-wo-om (rest (fifth world)))
                           (draw-oceania (sixth world)))))

(displayln "Type (help) for commands.")