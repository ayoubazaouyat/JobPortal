package teapot.collat_hbrs.views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import javax.annotation.security.PermitAll;
import teapot.collat_hbrs.backend.Company;
import teapot.collat_hbrs.backend.security.CompanyService;
import teapot.collat_hbrs.views.components.CompanyResultWidget;

import java.util.Set;

@Route(value = "companysearch", layout = MainLayout.class)
@PageTitle("Company Search | Coll@HBRS")
@PermitAll
public class CompanySearchView extends VerticalLayout {

    // TODO Get locations from database
    private final String[] cities = new String[]{"32049 Herford", "32051 Herford", "32052 Herford", "32105 Bad Salzuflen", "32107 Bad Salzuflen", "32108 Bad Salzuflen", "32120 Hiddenhausen", "32130 Enger", "32139 Spenge", "32257 Bünde", "32278 Kirchlengern", "32289 Rödinghausen", "32312 Lübbecke", "32339 Espelkamp", "32351 Stemwede", "32361 Preußisch Oldendorf", "32369 Rahden", "32423 Minden", "32425 Minden", "32427 Minden", "32429 Minden", "32457 Porta Westfalica", "32469 Petershagen", "32479 Hille", "32545 Bad Oeynhausen", "32547 Bad Oeynhausen", "32549 Bad Oeynhausen", "32584 Löhne", "32602 Vlotho", "32609 Hüllhorst", "32657 Lemgo", "32676 Lügde", "32683 Barntrup", "32689 Kalletal", "32694 Dörentrup", "32699 Extertal", "32756 Detmold", "32758 Detmold", "32760 Detmold", "32791 Lage", "32805 Horn-Bad Meinberg", "32816 Schieder-Schwalenberg", "32825 Blomberg", "32832 Augustdorf", "32839 Steinheim", "33014 Bad Driburg", "33034 Brakel", "33039 Nieheim", "33098 Paderborn", "33100 Paderborn", "33102 Paderborn", "33104 Paderborn", "33106 Paderborn", "33129 Delbrück", "33142 Büren", "33154 Salzkotten", "33161 Hövelhof", "33165 Lichtenau", "33175 Bad Lippspringe", "33178 Borchen", "33181 Wünnenberg", "33184 Altenbeken", "33189 Schlangen", "33330 Gütersloh", "33330 Gütersloh", "33332 Gütersloh", "33333 Bertelsmann", "33334 Gütersloh", "33335 Gütersloh", "33378 Rheda-Wiedenbrück", "33397 Rietberg", "33415 Verl", "33428 Harsewinkel", "33442 Herzebrock-Clarholz", "33449 Langenberg", "33602 Bielefeld", "33604 Bielefeld", "33605 Bielefeld", "33607 Bielefeld", "33609 Bielefeld", "33609 Bielefeld", "33611 Bielefeld", "33613 Bielefeld", "33615 Bielefeld", "33617 Bielefeld", "33619 Bielefeld", "33647 Bielefeld", "33649 Bielefeld", "33659 Bielefeld", "33689 Bielefeld", "33699 Bielefeld", "33719 Bielefeld", "33729 Bielefeld", "33739 Bielefeld", "33758 Schloß Holte-Stukenbrock", "33775 Versmold", "33790 Halle (Westfalen)", "33803 Steinhagen", "33813 Oerlinghausen", "33818 Leopoldshöhe", "33824 Werther (Westf.)", "33829 Borgholzhausen", "34414 Warburg", "34431 Marsberg", "34434 Borgentreich", "34439 Willebadessen", "37671 Höxter", "37688 Beverungen", "37696 Marienmünster", "40210 Düsseldorf", "40211 Düsseldorf", "40211 Düsseldorf", "40212 Düsseldorf", "40213 Düsseldorf", "40215 Düsseldorf", "40217 Düsseldorf", "40219 Düsseldorf", "40221 Düsseldorf", "40223 Düsseldorf", "40225 Düsseldorf", "40227 Düsseldorf", "40229 Düsseldorf", "40231 Düsseldorf", "40233 Düsseldorf", "40235 Düsseldorf", "40237 Düsseldorf", "40239 Düsseldorf", "40468 Düsseldorf", "40470 Düsseldorf", "40472 Düsseldorf", "40472 Düsseldorf", "40474 Düsseldorf", "40476 Düsseldorf", "40477 Düsseldorf", "40479 Düsseldorf", "40489 Düsseldorf", "40545 Düsseldorf", "40547 Düsseldorf", "40549 Düsseldorf", "40589 Düsseldorf", "40591 Düsseldorf", "40593 Düsseldorf", "40595 Düsseldorf", "40597 Düsseldorf", "40599 Düsseldorf", "40625 Düsseldorf", "40627 Düsseldorf", "40629 Düsseldorf", "40667 Meerbusch", "40668 Meerbusch", "40670 Meerbusch", "40699 Erkrath", "40721 Hilden, Düsseldorf", "40721 Hilden, Düsseldorf", "40723 Hilden", "40724 Hilden", "40764 Langenfeld", "40789 Monheim am Rhein", "40822 Mettmann", "40878 Ratingen", "40880 Ratingen", "40882 Ratingen", "40883 Ratingen", "40885 Ratingen", "41061 Mönchengladbach", "41061 Mönchengladbach", "41061 Mönchengladbach", "41063 Mönchengladbach", "41063 Mönchengladbach", "41065 Mönchengladbach", "41066 Mönchengladbach", "41068 Mönchengladbach", "41069 Mönchengladbach", "41069 Mönchengladbach", "41169 Mönchengladbach", "41179 Mönchengladbach", "41189 Mönchengladbach", "41199 Mönchengladbach", "41236 Mönchengladbach", "41238 Mönchengladbach", "41239 Mönchengladbach", "41334 Nettetal", "41352 Korschenbroich", "41363 Jüchen", "41366 Schwalmtal", "41372 Niederkrüchten", "41379 Brüggen", "41460 Neuss", "41462 Neuss", "41464 Neuss", "41466 Neuss", "41468 Neuss", "41469 Neuss", "41470 Neuss", "41472 Neuss", "41515 Grevenbroich", "41516 Grevenbroich", "41517 Grevenbroich", "41539 Dormagen", "41540 Dormagen", "41541 Dormagen", "41542 Dormagen", "41564 Kaarst", "41569 Rommerskirchen", "41747 Viersen", "41747 Viersen", "41747 Viersen", "41748 Viersen", "41748 Viersen", "41748 Viersen", "41749 Viersen", "41751 Viersen", "41812 Erkelenz", "41836 Hückelhoven", "41844 Wegberg", "41849 Wassenberg", "42103 Wuppertal", "42105 Wuppertal", "42107 Wuppertal", "42109 Wuppertal", "42111 Wuppertal", "42113 Wuppertal", "42115 Wuppertal", "42117 Wuppertal", "42119 Wuppertal", "42275 Wuppertal", "42277 Wuppertal", "42279 Wuppertal", "42281 Wuppertal", "42283 Wuppertal", "42285 Wuppertal", "42287 Wuppertal", "42289 Wuppertal", "42327 Wuppertal", "42329 Wuppertal", "42349 Wuppertal", "42369 Wuppertal", "42389 Wuppertal", "42399 Wuppertal", "42477 Radevormwald", "42489 Wülfrath", "42499 Hückeswagen", "42549 Velbert", "42551 Velbert", "42553 Velbert", "42555 Velbert", "42579 Heiligenhaus", "42651 Solingen", "42653 Solingen", "42655 Solingen", "42657 Solingen", "42659 Solingen", "42697 Solingen", "42699 Solingen", "42719 Solingen", "42781 Haan", "42799 Leichlingen", "42853 Remscheid", "42855 Remscheid", "42857 Remscheid", "42859 Remscheid", "42897 Remscheid", "42899 Remscheid", "42929 Wermelskirchen", "44135 Dortmund", "44137 Dortmund", "44139 Dortmund", "44141 Dortmund", "44143 Dortmund", "44145 Dortmund", "44147 Dortmund", "44149 Dortmund", "44225 Dortmund", "44227 Dortmund", "44229 Dortmund", "44263 Dortmund", "44265 Dortmund", "44267 Dortmund", "44269 Dortmund", "44287 Dortmund", "44289 Dortmund", "44309 Dortmund", "44319 Dortmund", "44328 Dortmund", "44329 Dortmund", "44339 Dortmund", "44357 Dortmund", "44359 Dortmund", "44369 Dortmund", "44379 Dortmund", "44388 Dortmund", "44532 Lünen", "44534 Lünen", "44536 Lünen", "44575 Castrop-Rauxel", "44577 Castrop-Rauxel", "44577 Castrop-Rauxel", "44577 Castrop-Rauxel", "44579 Castrop-Rauxel", "44581 Castrop-Rauxel", "44623 Herne", "44623 Herne", "44623 Herne", "44625 Herne", "44627 Herne", "44628 Herne", "44629 Herne", "44649 Herne", "44651 Herne", "44652 Herne", "44653 Herne", "44787 Bochum", "44789 Bochum", "44791 Bochum", "44793 Bochum", "44795 Bochum", "44797 Bochum", "44799 Bochum", "44799 Bochum", "44801 Bochum", "44801 Bochum", "44803 Bochum", "44805 Bochum", "44807 Bochum", "44809 Bochum", "44866 Bochum", "44867 Bochum", "44867 Bochum", "44869 Bochum", "44879 Bochum", "44892 Bochum", "44894 Bochum", "45127 Essen", "45128 Essen", "45130 Essen", "45131 Essen", "45133 Essen", "45134 Essen", "45136 Essen", "45138 Essen", "45139 Essen", "45141 Essen", "45143 Essen", "45144 Essen", "45145 Essen", "45147 Essen", "45149 Essen", "45219 Essen", "45239 Essen", "45257 Essen", "45259 Essen", "45276 Essen", "45277 Essen", "45279 Essen", "45289 Essen", "45307 Essen", "45309 Essen", "45326 Essen", "45327 Essen", "45329 Essen", "45355 Essen", "45356 Essen", "45357 Essen", "45359 Essen", "45468 Mülheim an der Ruhr", "45470 Mülheim an der Ruhr", "45472 Mülheim an der Ruhr", "45473 Mülheim an der Ruhr", "45475 Mülheim an der Ruhr", "45476 Mülheim an der Ruhr", "45478 Mülheim an der Ruhr", "45479 Mülheim an der Ruhr", "45481 Mülheim an der Ruhr", "45525 Hattingen", "45527 Hattingen", "45529 Hattingen", "45549 Sprockhövel", "45657 Recklinghausen", "45659 Recklinghausen", "45661 Recklinghausen", "45663 Recklinghausen", "45665 Recklinghausen", "45699 Herten", "45701 Herten", "45711 Datteln", "45721 Haltern am See", "45731 Waltrop", "45739 Oer-Erkenschwick", "45768 Marl", "45770 Marl", "45772 Marl", "45879 Gelsenkirchen", "45881 Gelsenkirchen", "45883 Gelsenkirchen", "45884 Gelsenkirchen Rotthausen", "45886 Gelsenkirchen", "45888 Gelsenkirchen", "45889 Gelsenkirchen", "45891 Gelsenkirchen", "45892 Gelsenkirchen", "45894 Gelsenkirchen", "45896 Gelsenkirchen", "45897 Gelsenkirchen", "45897 Gelsenkirchen", "45899 Gelsenkirchen", "45964 Gladbeck", "45966 Gladbeck", "45968 Gladbeck", "46045 Oberhausen", "46047 Oberhausen", "46049 Oberhausen", "46117 Oberhausen", "46119 Oberhausen", "46145 Oberhausen", "46147 Oberhausen", "46149 Oberhausen", "46236 Bottrop", "46238 Bottrop", "46240 Bottrop", "46242 Bottrop", "46244 Bottrop", "46282 Dorsten", "46284 Dorsten", "46286 Dorsten", "46325 Borken", "46342 Velen", "46348 Raesfeld", "46354 Südlohn", "46359 Heiden", "46395 Bocholt", "46397 Bocholt", "46399 Bocholt", "46414 Rhede", "46419 Isselburg", "46446 Emmerich am Rhein", "46459 Rees", "46483 Wesel", "46485 Wesel", "46487 Wesel", "46499 Hamminkeln", "46509 Xanten", "46514 Schermbeck", "46519 Alpen", "46535 Dinslaken", "46537 Dinslaken", "46539 Dinslaken", "46562 Voerde (Niederrhein)", "46569 Hünxe", "47051 Duisburg", "47053 Duisburg", "47055 Duisburg", "47057 Duisburg", "47058 Duisburg", "47059 Duisburg", "47119 Duisburg", "47137 Duisburg", "47138 Duisburg", "47139 Duisburg", "47166 Duisburg", "47167 Duisburg", "47169 Duisburg", "47178 Duisburg", "47179 Duisburg", "47198 Duisburg", "47199 Duisburg", "47226 Duisburg", "47228 Duisburg", "47229 Duisburg", "47239 Duisburg", "47249 Duisburg", "47259 Duisburg", "47269 Duisburg", "47279 Duisburg", "47441 Moers", "47443 Moers", "47445 Moers", "47447 Moers", "47475 Kamp-Lintfort", "47495 Rheinberg", "47506 Neukirchen-Vluyn", "47509 Rheurdt", "47533 Kleve", "47546 Kalkar", "47551 Bedburg-Hau", "47559 Kranenburg", "47574 Goch", "47589 Uedem", "47608 Geldern", "47623 Kevelaer-Mitte", "47624 Kevelaer-Twisteden", "47625 Kevelaer-Wetten", "47626 Kevelaer-Winnekendonk", "47627 Kevelaer-Kervenheim", "47638 Straelen", "47647 Kerken", "47652 Weeze", "47661 Issum", "47665 Sonsbeck", "47669 Wachtendonk", "47798 Krefeld", "47799 Krefeld", "47800 Krefeld", "47802 Krefeld", "47803 Krefeld", "47804 Krefeld", "47805 Krefeld", "47807 Krefeld", "47809 Krefeld", "47829 Krefeld", "47839 Krefeld", "47877 Willich", "47906 Kempen", "47918 Tönisvorst", "47929 Grefrath", "48143 Münster", "48145 Münster", "48147 Münster", "48149 Münster", "48151 Münster", "48153 Münster", "48155 Münster", "48157 Münster", "48159 Münster", "48161 Münster", "48163 Münster", "48165 Münster", "48167 Münster", "48231 Warendorf", "48249 Dülmen", "48268 Greven", "48282 Emsdetten", "48291 Telgte", "48301 Nottuln", "48308 Senden", "48317 Drensteinfurt", "48324 Sendenhorst", "48329 Havixbeck", "48336 Sassenberg", "48341 Altenberge", "48346 Ostbevern", "48351 Everswinkel", "48356 Nordwalde", "48361 Beelen", "48366 Laer", "48369 Saerbeck", "48429 Rheine", "48431 Rheine", "48432 Rheine", "48432 Rheine", "48477 Hörstel", "48485 Neuenkirchen", "48493 Wettringen", "48496 Hopsten", "48565 Steinfurt", "48599 Gronau", "48607 Ochtrup", "48612 Horstmar", "48619 Heek", "48624 Schöppingen", "48629 Metelen", "48653 Coesfeld", "48683 Ahaus", "48691 Vreden", "48703 Stadtlohn", "48712 Gescher", "48720 Rosendahl", "48727 Billerbeck", "48734 Reken", "48739 Legden", "49477 Ibbenbüren", "49479 Ibbenbüren", "49492 Westerkappeln", "49497 Mettingen", "49504 Lotte", "49509 Recke", "49525 Lengerich", "49536 Lienen", "49545 Tecklenburg", "49549 Ladbergen", "50126 Bergheim", "50127 Bergheim", "50129 Bergheim", "50169 Kerpen", "50170 Kerpen", "50170 Kerpen", "50171 Kerpen", "50181 Bedburg", "50189 Elsdorf", "50226 Frechen", "50259 Pulheim", "50321 Brühl", "50354 Hürth", "50374 Erftstadt", "50389 Wesseling", "50667 Köln", "50667 Köln", "50668 Köln", "50670 Köln", "50672 Köln", "50674 Köln", "50676 Köln", "50677 Köln", "50678 Köln", "50679 Köln", "50733 Köln", "50735 Köln", "50735 Köln", "50737 Köln", "50739 Köln", "50765 Köln", "50767 Köln", "50769 Köln", "50823 Köln", "50825 Köln", "50827 Köln", "50829 Köln", "50858 Köln", "50859 Köln", "50931 Köln", "50933 Köln", "50935 Köln", "50937 Köln", "50939 Köln", "50939 Köln", "50968 Köln", "50969 Köln", "50996 Köln", "50997 Köln", "50999 Köln", "51061 Köln", "51063 Köln", "51065 Köln", "51067 Köln", "51069 Köln", "51103 Köln", "51105 Köln", "51107 Köln", "51109 Köln", "51143 Köln", "51145 Köln", "51147 Köln", "51149 Köln", "51371 Leverkusen", "51373 Leverkusen", "51375 Leverkusen", "51377 Leverkusen", "51379 Leverkusen", "51381 Leverkusen", "51399 Burscheid", "51427 Bergisch Gladbach", "51429 Bergisch Gladbach", "51465 Bergisch Gladbach", "51467 Bergisch Gladbach", "51467 Bergisch Gladbach", "51469 Bergisch Gladbach", "51491 Overath", "51503 Rösrath", "51515 Kürten", "51519 Odenthal", "51545 Waldbröl", "51570 Windeck", "51580 Reichshof", "51588 Nümbrecht", "51597 Morsbach", "51643 Gummersbach", "51645 Gummersbach", "51647 Gummersbach", "51674 Wiehl", "51688 Wipperfürth", "51702 Bergneustadt", "51709 Marienheide", "51766 Engelskirchen", "51789 Lindlar", "52062 Aachen", "52064 Aachen", "52066 Aachen", "52068 Aachen", "52070 Aachen", "52072 Aachen", "52074 Aachen", "52076 Aachen", "52078 Aachen", "52080 Aachen", "52134 Herzogenrath", "52146 Würselen", "52152 Simmerath", "52152 Simmerath", "52156 Monschau", "52156 Monschau", "52156 Monschau", "52156 Monschau", "52159 Roetgen", "52159 Roetgen", "52159 Roetgen", "52222 Stolberg (Rhld.)", "52222 Stolberg (Rhld.)", "52223 Stolberg (Rhld.)", "52224 Stolberg", "52249 Eschweiler", "52349 Düren", "52351 Düren", "52353 Düren", "52355 Düren", "52372 Kreuzau", "52379 Langerwehe", "52382 Niederzier", "52385 Nideggen", "52388 Nörvenich", "52391 Vettweiß", "52393 Hürtgenwald", "52396 Heimbach", "52399 Merzenich", "52428 Jülich", "52441 Linnich", "52445 Titz", "52457 Aldenhoven", "52459 Inden", "52477 Alsdorf", "52499 Baesweiler", "52511 Geilenkirchen", "52525 Waldfeucht, Heinsberg", "52531 Übach-Palenberg", "52538 Gangelt, Selfkant", "53111 Bonn", "53113 Bonn", "53115 Bonn", "53117 Bonn", "53119 Bonn", "53121 Bonn", "53123 Bonn", "53125 Bonn", "53127 Bonn", "53129 Bonn", "53173 Bonn", "53175 Bonn", "53177 Bonn", "53179 Bonn", "53225 Bonn", "53227 Bonn", "53229 Bonn", "53332 Bornheim", "53340 Meckenheim", "53343 Wachtberg", "53347 Alfter", "53359 Rheinbach", "53604 Bad Honnef", "53639 Königswinter", "53721 Siegburg", "53757 Sankt Augustin", "53773 Hennef (Sieg)", "53783 Eitorf", "53797 Lohmar", "53804 Much", "53809 Ruppichteroth", "53819 Neunkirchen-Seelscheid", "53840 Troisdorf", "53842 Troisdorf", "53844 Troisdorf", "53859 Niederkassel", "53879 Euskirchen", "53879 Euskirchen", "53881 Euskirchen", "53894 Mechernich", "53902 Bad Münstereifel", "53909 Zülpich", "53913 Swisttal", "53919 Weilerswist", "53925 Kall", "53937 Schleiden", "53940 Hellenthal", "53945 Blankenheim", "53947 Nettersheim", "53949 Dahlem", "57072 Siegen", "57074 Siegen", "57076 Siegen", "57078 Siegen", "57080 Siegen", "57223 Kreuztal", "57234 Wilnsdorf", "57250 Netphen", "57258 Freudenberg", "57271 Hilchenbach", "57290 Neunkirchen", "57299 Burbach", "57319 Bad Berleburg", "57334 Bad Laasphe", "57339 Erndtebrück", "57368 Lennestadt", "57392 Schmallenberg", "57399 Kirchhundem", "57413 Finnentrop", "57439 Attendorn", "57462 Olpe", "57482 Wenden", "57489 Drolshagen", "58089 Hagen", "58089 Hagen", "58091 Hagen", "58093 Hagen", "58093 Hagen", "58095 Hagen", "58095 Hagen", "58097 Hagen", "58099 Hagen", "58099 Hagen", "58119 Hagen", "58135 Hagen", "58239 Schwerte", "58256 Ennepetal", "58285 Gevelsberg", "58300 Wetter (Ruhr)", "58313 Herdecke", "58332 Schwelm", "58339 Breckerfeld", "58452 Witten", "58453 Witten", "58454 Witten", "58455 Witten", "58456 Witten", "58507 Lüdenscheid", "58509 Lüdenscheid", "58511 Lüdenscheid", "58513 Lüdenscheid", "58515 Lüdenscheid", "58540 Meinerzhagen", "58553 Halver", "58566 Kierspe", "58579 Schalksmühle", "58636 Iserlohn", "58638 Iserlohn", "58640 Iserlohn", "58642 Iserlohn", "58644 Iserlohn", "58675 Hemer", "58706 Menden", "58708 Menden", "58710 Menden", "58730 Fröndenberg/Ruhr", "58739 Wickede (Ruhr)", "58762 Altena", "58769 Nachrodt-Wiblingwerde", "58791 Werdohl", "58802 Balve", "58809 Neuenrade", "58840 Plettenberg", "58849 Herscheid", "59063 Hamm", "59065 Hamm", "59067 Hamm", "59069 Hamm", "59071 Hamm", "59073 Hamm", "59075 Hamm", "59077 Hamm", "59174 Kamen", "59192 Bergkamen", "59199 Bönen", "59227 Ahlen", "59229 Ahlen", "59269 Beckum", "59302 Oelde", "59320 Ennigerloh", "59329 Wadersloh", "59348 Lüdinghausen", "59368 Werne", "59379 Selm", "59387 Ascheberg", "59394 Nordkirchen", "59399 Olfen", "59423 Unna", "59425 Unna", "59425 Unna", "59427 Unna", "59439 Holzwickede", "59457 Werl", "59469 Ense", "59494 Soest", "59505 Bad Sassendorf", "59510 Lippetal", "59514 Welver", "59519 Möhnesee", "59555 Lippstadt", "59556 Lippstadt", "59557 Lippstadt", "59558 Lippstadt", "59581 Warstein", "59590 Geseke", "59597 Erwitte", "59602 Rüthen", "59609 Anröchte", "59755 Arnsberg", "59757 Arnsberg", "59759 Arnsberg", "59821 Arnsberg", "59823 Arnsberg", "59846 Sundern", "59872 Meschede", "59889 Eslohe", "59909 Bestwig", "59929 Brilon", "59939 Olsberg", "59955 Winterberg", "59964 Medebach", "59969 Bromskirchen, Hallenberg"};
    private CompanyService companyService;
    Scroller resultsContainer;

    /**
     * Constructor
     */
    public CompanySearchView(CompanyService companyService) {
        this.companyService = companyService;
        H1 heading = new H1("Company Search");
        FormLayout search = initSearch();
        Hr separator = new Hr();
        resultsContainer = new Scroller(); //buildResultsContainer();

        add(
                heading,
                search,
                separator,
                resultsContainer
        );

        setHeightFull();
    }

    /**
     * Creates the search fields
     */
    private FormLayout initSearch() {
        var searchLayout = new FormLayout();
        var companyField = new TextField("Company Name");
        var locationSelector = new MultiSelectComboBox<String>("Location");
        var categorySelector = new MultiSelectComboBox<String>("Industry");
        var searchButton = new Button("Search");

        locationSelector.setItems(cities); // TODO Demo values, get real values from database
        locationSelector.setClearButtonVisible(true);
        categorySelector.setClearButtonVisible(true);
        categorySelector.setItems("Game Studio", "Hosting", "Telecommunication", "Consulting", "E-Commerce"); // TODO Demo values, get real values from database
        searchButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        searchButton.setIcon(new Icon(VaadinIcon.SEARCH));
        searchButton.addClickShortcut(Key.ENTER);
        searchButton.addClickListener(buttonClickEvent -> {
            this.remove(resultsContainer);
            resultsContainer = buildResultsContainer(companyField.getValue(), locationSelector.getSelectedItems(),categorySelector.getSelectedItems());
            this.add(resultsContainer);
        });

        searchLayout.add(
                companyField,
                locationSelector,
                categorySelector,
                searchButton
        );

        searchLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2),
                new FormLayout.ResponsiveStep("1000px", 4)
        );

        return searchLayout;
    }

    private Scroller buildResultsContainer(String name, Set<String> location, Set<String> catogory) {
        VerticalLayout results = new VerticalLayout();

        // Demo companies
        Company com = new Company("microsoft", "", "Microsoft", "Cologne", "", "", "");

        CompanyResultWidget[] companies = new CompanyResultWidget[10];
        for (int x = 0; x < companies.length; x++) {
            companies[x] = new CompanyResultWidget(com);
            results.add(companies[x]);
        }
        // ------------------------

        // TODO Add job ads from database (in the future: including filtering)
        for (Company company: companyService.getFilteredCompanies(name,location,catogory)
             ) {
            results.add(new CompanyResultWidget(company));

        }

        Scroller scroller = new Scroller(results);
        scroller.setWidthFull();
        scroller.setHeightFull();
        scroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);



        return scroller;
    }

}
