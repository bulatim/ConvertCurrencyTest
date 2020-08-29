package ru.bulat.currencyconvertertest.model.xml

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "ValCurs")
class ValCurs {
    @field:ElementList(inline = true, name = "Valute")
    lateinit var valuties: List<Valute>
    @field:Attribute(name = "Date")
    var Date: String? = null
    @field:Attribute(name = "name")
    var name: String? = null
}