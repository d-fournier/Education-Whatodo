# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('whatodo', '0001_initial'),
    ]

    operations = [
        migrations.AlterField(
            model_name='whatodouser',
            name='username',
            field=models.CharField(max_length=30),
            preserve_default=True,
        ),
    ]
