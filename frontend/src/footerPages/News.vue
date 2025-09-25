<template>
  <div class="timeline">
    <div class="container right">
      <div class="content">
        <h2> 21.11.2020 |<small> 12:15</small></h2>
        <p> US President Trump's attempts to contest the election results in states where his challenger Joe Biden won remains unsuccessful.
          Recounts in the US state of Georgia, among others, conclude that Biden was able to secure the majority of ballots there.
          Elsewhere, lawsuits from Trump's campaign led nowhere. The incumbent US president continues to claim that the election was rigged and that he actually won.
          Meanwhile, several Republicans are also calling for the transition phase to be initiated in order to clear the way for US President-elect Joe Biden to take office.</p>
      </div>
    </div>
    <div class="container left">
      <div class="content">
        <h2> 21.11.2020 |<small> 12:00</small></h2>
        <p>In Leipzig (Germany), the police are preparing for another large-scale demonstration by Corona skeptics. In the run-up to the so-called "Querdenken" demonstration, both
          Right-wing extremists as well as left-wing extremists mobilized at counter-demonstrations. Both a stronger police presence and metal grilles and
          Identity checks are intended to prevent attacks on press representatives and prevent riots, as well as non-compliance with the Corona rules.
          The police are expecting several 1000 participants.</p>
      </div>
    </div>
    <div class="container right">
      <div class="content">
        <h2> 21.11.2020 |<small> 11:30</small></h2>
        <p> New corona infections in Germany are still at a very high level. The Robert Koch Institute reported 22964 new infections within 24 hours,
          according to all health authorities.</p>
      </div>
    </div>
    <div class="container left">
      <div class="content">
        <h2> 20.11.2020 |<small> 14:30</small></h2>
        <p>After right-wing media activists who were allegedly infiltrated into the German Bundestag by 3 AFD deputies on Wednesday and there
          have deliberately harassed members of parliament and in some cases invaded the offices of the members of parliament without authority, the
          Bundestag debated in a current hour about this incident. The debate in the plenary hall showed fierce accusations against the AFD with
          various accusations that they are acting undemocratically and ridiculing Parliament. The tone was particularly strong among the parliamentary group
          of the CDU very hard. The AFD expressed apologies with the speech of the group chairman Alexander Gauland and rejected the allegations.
          Gauland's speech, on the other hand, was described as "hypocrisy". In view of the incident on Wednesday, the head of the Bundestag is considering,
          to take criminal action.</p>
      </div>
    </div>
    <div class="container right">
      <div class="content">
        <h2> 20.11.2020 |<small> 7:30</small></h2>
        <p>According to the Robert Koch Institute, the number of new corona infections in Germany has reached a new high. According to the information,
          of all health authorities, 23,648 new infections were reported to the RKI within 24 hours throughout Germany.</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "News"
}
</script>

<style scoped>
*
{
  box-sizing: border-box;
}
.timeline
{
  background: beige;
  position: relative;
}
.timeline::after
{
  content: '';
  position: absolute;
  width: 8px;
  background-color: #fdc3c3;
  top: 0;
  bottom: 0;
  left: 50%;
  margin-left: -3px;
}
.container
{
  padding: 15px;
  position: relative;
  width: 50%;
}
.container::after
{
  content: '';
  position: absolute;
  width: 22px;
  height: 22px;
  right: -15px;
  background-color: white;
  border: 3px solid #FF9F55;
  top: 10px;
  border-radius: 50%;
  z-index: 1;
}
.left
{
  left: 0;
}
.right
{
  left: 50%;
}
.left::before
{
  content: " ";
  height: 0;
  position: absolute;
  top: 22px;
  width: 0;
  z-index: 1;
  right: 30px;
  border-width: 10px 0 10px 10px;
  border-color: transparent transparent transparent white;
}
.right::before
{
  content: " ";
  height: 0;
  position: absolute;
  top: 22px;
  width: 0;
  z-index: 1;
  left: 30px;
  border-width: 10px 10px 10px 0;
  border-color: transparent white transparent transparent;
}
.right::after
{
  left: -13px;
}
.content
{
  font-size: 0.85em;
  padding: 3px 12px;
  background-color: #cdfcff;
  position: relative;
  border-radius: 6px;
  box-shadow: 3px 3px 10px -2px;
}
@media screen and (max-width: 750px)
{
  .timeline::after
  {
    left: 25px;
    margin-left: 0;
  }
  .container
  {
    width: 100%;
    padding-left: 50px;
    padding-right: 15px;
  }
  .left::after, .right::after
  {
    left: 15px;
  }
  .right
  {
    left: 0;
  }
  body
  {
    font-size: 90%;
  }
}
@media screen and (max-width: 450px)
{
  body
  {
    font-size: 70%;
  }
}
</style>